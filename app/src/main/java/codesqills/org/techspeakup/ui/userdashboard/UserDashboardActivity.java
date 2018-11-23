package codesqills.org.techspeakup.ui.userdashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsActivity;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsContract;
import codesqills.org.techspeakup.ui.home.HomeActivity;
import codesqills.org.techspeakup.ui.map.MapActivity;
import codesqills.org.techspeakup.ui.search.SearchFragment;
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileActivity;
import codesqills.org.techspeakup.ui.events.EventsActivity;
import codesqills.org.techspeakup.ui.followers.FollowersActivity;
import codesqills.org.techspeakup.ui.newnotification.NewNotificationActivity;
import codesqills.org.techspeakup.ui.settings.SettingsFragment;
import codesqills.org.techspeakup.ui.signin.SignInActivity;
import codesqills.org.techspeakup.ui.speaker.SpeakerFragment;
import codesqills.org.techspeakup.ui.speakerprofile.SpeakerProfileActivity;
import codesqills.org.techspeakup.utils.GpsTracker;
import codesqills.org.techspeakup.utils.SectionsPagerAdapter;

/**
 * Created by kamalshree on 11/16/2018.
 */

public class UserDashboardActivity extends AppCompatActivity implements UserDashboardContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "UserDashboardActivity";

    private UserDashboardContract.Presenter mPresenter;

    private ViewPager mViewPager;
    private GpsTracker gpsTracker;


    @BindView(R.id.navigation_home_speaker)
    BottomNavigationViewEx bnve;
    @BindView(R.id.user_navigationview_home)
    NavigationView usernavigationview_home;
    @BindView(R.id.details_page_toolbar_menu)
    ImageView details_page_toolbar_menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private Toolbar myToolbar;
    private static final int BACK_PRESS_DURATION = 3000;
    private TextView myUsername;

    boolean mTwiceClicked = false;
    Snackbar mSnackbar;
    SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        ButterKnife.bind(this);
        mViewPager = findViewById(R.id.container);


        intializeUI();
        initFCM();
        setupViewPager();
        getLatLong();

        // Injecting Presenter here
        PresenterInjector.injectUserDashboardPresenter(this);

        mPresenter.start(getIntent().getExtras());

        usernavigationview_home.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_drawer_edit_profile:
                                mPresenter.handleEditProfile();
                                break;
                            case R.id.navigation_drawer_logout:
                                showSignOutAlert();
                                break;
                            default:
                                break;
                        }
                        drawerLayout.closeDrawers();  // CLOSE DRAWER
                        return false;
                    }
                });


        // Handle menu item clicks here.
        details_page_toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //BottomNavigationClick
        bnve.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Responsible for adding the 3 tabs: Speakers, Search, Settings
     */
    private void setupViewPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new SettingsFragment()); //index 0
        mSectionsPagerAdapter.addFragment(new SpeakerFragment()); //index 2
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.menu_speaker_highlight);

    }


    //Click on hamburger icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_speaker_profile:
                drawerLayout.openDrawer(GravityCompat.END);// OPEN DRAWER
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Intialize UI
    private void intializeUI() {
        myToolbar = (Toolbar) findViewById(R.id.details_page_toolbar);

        //access the Navigation header element.
        View myheaderView = usernavigationview_home.getHeaderView(0);
        myUsername = (TextView) myheaderView.findViewById(R.id.navigation_drawer_welcome_text);

        bnve.enableAnimation(true);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void loadMyUserName(String username) {
        myUsername.setText("Welcome " + username);

    }

    @Override
    public void setPresenter(UserDashboardContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer: sending token to server: " + token);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("messaging_token")
                .setValue(token);

        //setting follower count val to 0
        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("followers_count")
                .setValue("0");
    }


    private void initFCM() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "initFCM: token: " + token);
        sendRegistrationToServer(token);
    }


    @Override
    public void displayUserProfile() {
        String userKey =FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent userProfileIntent = new Intent(this, FollowersDetailsActivity.class);
        userProfileIntent.putExtra(FollowersDetailsContract.KEY_FOLLOWERS_ID, userKey);
        startActivity(userProfileIntent);
    }

    @Override
    public void displayEditProfile() {
        Intent editProfileIntent = new Intent(this, SpeakerEditProfileActivity.class);
        startActivity(editProfileIntent);
    }

    @Override
    public void displaySpeakers() {
        Intent SpeakersIntent = new Intent(this, MapActivity.class);
        startActivity(SpeakersIntent);
    }

    @Override
    public void displayEvent() {
        Intent eventIntent = new Intent(this, EventsActivity.class);
        startActivity(eventIntent);
    }

    @Override
    public void displayNotification() {
        Intent eventIntent = new Intent(this, NewNotificationActivity.class);
        startActivity(eventIntent);
    }

    //Bottom Navigation bar Click items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_speaker_profile:
                mPresenter.handleUserProfile();
                break;
            case R.id.navigation_speaker_user:
                mPresenter.handleSpeakers();
                break;
            case R.id.navigation_speaker_notification:
                mPresenter.handleNotification();
                break;
            case R.id.navigation_speaker_event:
                mPresenter.handleUserEvent();
                break;
        }

        return false;
    }

    //exit functionality
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (mTwiceClicked) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                mSnackbar.dismiss();
            } else {
                mTwiceClicked = true;
                showSnackBar(R.string.home_back_btn_msg);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTwiceClicked = false;
                    }
                }, BACK_PRESS_DURATION);
            }

        }
    }

    //Signout implementation
    private void showSignOutAlert() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.sign_out_title))
                .setMessage(getString(R.string.sign_out_message))
                .setPositiveButton(getResources().getString(R.string.sign_out_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser != null) {
                                FirebaseAuth.getInstance().signOut();
                                // DataHandlerProvider.provide().destroy();
                                Intent signInIntent = new Intent(UserDashboardActivity.this, SignInActivity.class);
                                UserDashboardActivity.this.startActivity(signInIntent);
                                //this.finishAffinity();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.sign_out_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    //Snackbar implementation
    private void showSnackBar(int string) {
        String msg = getResources().getString(string);
        mSnackbar = Snackbar.make(findViewById(R.id.drawer_layout), msg, Snackbar.LENGTH_LONG);
        final View snackbarView = mSnackbar.getView();
        TextView tvSnackbar = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tvSnackbar.setTextColor(getResources().getColor(R.color.colorAccent));
        snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        mSnackbar.show();

        snackbarView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                snackbarView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }


    /* get Users Latitude and Longitude */
    private void getLatLong() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            getLocation();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    public void getLocation(){
        gpsTracker = new GpsTracker(UserDashboardActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            if(latitude!=0 && longitude!=0){
                sendUserLocationDetails(latitude, longitude);
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void sendUserLocationDetails(double latitude, double longitude) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("user_latitude")
                .setValue(latitude);

        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("user_longitude")
                .setValue(longitude);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }
}
