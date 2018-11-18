package codesqills.org.techspeakup.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
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
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileActivity;
import codesqills.org.techspeakup.ui.events.EventsActivity;
import codesqills.org.techspeakup.ui.followers.FollowersActivity;
import codesqills.org.techspeakup.ui.newnotification.NewNotificationActivity;
import codesqills.org.techspeakup.ui.signin.SignInActivity;
import codesqills.org.techspeakup.ui.speakerprofile.SpeakerProfileActivity;
import codesqills.org.techspeakup.utils.GpsTracker;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class HomeActivity extends AppCompatActivity implements HomeContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "HomeActivity";

    private Bundle extras;
    private HomeContract.Presenter mPresenter;
    private TextView myUsername;
    private Toolbar myToolbar;
    private static final int BACK_PRESS_DURATION = 3000;
    private GpsTracker gpsTracker;

    @BindView(R.id.navigation_home_speaker)
    BottomNavigationViewEx bnve;
    @BindView(R.id.navigationview_home)
    NavigationView navigationview_home;
    @BindView(R.id.details_page_toolbar_menu)
    ImageView details_page_toolbar_menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    boolean mTwiceClicked = false;
    Snackbar mSnackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_speaker);
        ButterKnife.bind(this);
        intializeUI();
        initFCM();
        getLatLong();

        PresenterInjector.injectHomePresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);

        navigationview_home.setNavigationItemSelectedListener(
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
        View headerView = navigationview_home.getHeaderView(0);
        myUsername = (TextView) headerView.findViewById(R.id.navigation_drawer_welcome_text);

        bnve.enableAnimation(true);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        setSupportActionBar(myToolbar);

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void statusProfileDetails() {
        //Toast.makeText(this, "Profile uploaded successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMyUserName(String username) {
        myUsername.setText("Welcome " + username);
        statusProfileDetails();
    }

    @Override
    public void displayUserProfile() {
        Intent userProfileIntent = new Intent(this, SpeakerProfileActivity.class);
        startActivity(userProfileIntent);
    }

    @Override
    public void displayEditProfile() {
        Intent editProfileIntent = new Intent(this, SpeakerEditProfileActivity.class);
        startActivity(editProfileIntent);
    }

    @Override
    public void displayFollowers() {
        Intent followersIntent = new Intent(this, FollowersActivity.class);
        startActivity(followersIntent);
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
            case R.id.navigation_speaker_follower:
                mPresenter.handleUserFollowers();
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
                                Intent signInIntent = new Intent(HomeActivity.this, SignInActivity.class);
                                HomeActivity.this.startActivity(signInIntent);
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

    @Override
    protected void onPause() {
        super.onPause();
        getLocation();
    }

    private void initFCM(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "initFCM: token: " + token);
        sendRegistrationToServer(token);
    }

    public void getLocation(){
        gpsTracker = new GpsTracker(HomeActivity.this);
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
}
