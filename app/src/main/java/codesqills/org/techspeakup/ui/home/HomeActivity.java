package codesqills.org.techspeakup.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileActivity;
import codesqills.org.techspeakup.ui.events.EventsActivity;
import codesqills.org.techspeakup.ui.speakerprofile.SpeakerProfileActivity;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class HomeActivity extends AppCompatActivity implements HomeContract.View, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Bundle extras;
    private HomeContract.Presenter mPresenter;
    private TextView myUsername;
    private Toolbar myToolbar;


    @BindView(R.id.navigation_home_speaker)
    BottomNavigationViewEx bnve;
    @BindView(R.id.linear_layout)
    LinearLayout linear_layout;
    @BindView(R.id.navigationview_home)
    NavigationView navigationview_home;
    @BindView(R.id.details_page_toolbar_menu)
    ImageView details_page_toolbar_menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;
    @BindView(R.id.refresh)
    Button refreshBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_speaker);
        ButterKnife.bind(this);
        intializeUI();

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }

        PresenterInjector.injectHomePresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);

        navigationview_home.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Handle menu item clicks here.
                        drawerLayout.closeDrawers();  // CLOSE DRAWER
                        mPresenter.handleEditProfile();
                        return true;
                    }
                });

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
        refreshBtn.setOnClickListener(this);
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
    public void displayEvent() {
        Intent eventIntent = new Intent(this, EventsActivity.class);
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
                break;
            case R.id.navigation_speaker_notification:
                break;
            case R.id.navigation_speaker_event:
                mPresenter.handleUserEvent();
                break;
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.speaker_profile_page_back:
                onBackPressed();
                break;
            case R.id.refresh:
                checkInternet();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }


    private void checkInternet() {
        if (NetworkUtils.connectionStatus(this)) {
            bnve.setVisibility(View.VISIBLE);
            linear_layout.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        bnve.setVisibility(View.INVISIBLE);
        linear_layout.setVisibility(View.INVISIBLE);
        layout_internet.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.VISIBLE);
        refreshBtn.setVisibility(View.VISIBLE);
    }

    /* No Internet Dialog */
    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(getString(R.string.no_internet_title));
        builder.setMessage(getString(R.string.no_internet_message));

        builder.setPositiveButton(getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        return builder;
    }
}
