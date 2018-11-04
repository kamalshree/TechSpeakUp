package codesqills.org.techspeakup.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private Bundle extras;
    private HomeContract.Presenter mPresenter;
    private TextView myUsername;

    @BindView(R.id.navigation_home_speaker)
    BottomNavigationViewEx bnve;
    @BindView(R.id.navigationview_home)
    NavigationView navigationview_home;
    @BindView(R.id.details_page_toolbar_menu)
    ImageView details_page_toolbar_menu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.details_page_toolbar)
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_speaker);
        ButterKnife.bind(this);
        intializeUI();

        PresenterInjector.injectHomePresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);

        navigationview_home.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Handle menu item clicks here.
                        drawerLayout.closeDrawers();  // CLOSE DRAWER
                        return true;
                    }
                });

        details_page_toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_speaker_profile:
                drawerLayout.openDrawer(GravityCompat.END);  // OPEN DRAWER
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void intializeUI() {
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
        Toast.makeText(this, "Profile uploaded successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMyUserName(String username) {
        myUsername.setText("Welcome " + username);
        statusProfileDetails();
    }
}
