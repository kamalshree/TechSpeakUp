package codesqills.org.techspeakup.ui.followers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileActivity;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsActivity;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsContract;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsActivity;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsContract;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/9/2018.
 */

public class FollowersActivity extends AppCompatActivity implements FollowersAdapter.FollowersItemListener, FollowersContract.View, View.OnClickListener {
    private static final String TAG = "FollowersActivity";

    private FollowersContract.Presenter mPresenter;
    private Bundle extras;
    private RecyclerView mFollowersRecyclerView;
    private FollowersAdapter mFollowersAdapter;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;
    @BindView(R.id.tv_no_internet)
    TextView noInternet;
    @BindView(R.id.refresh)
    Button refreshBtn;
    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.toolbar_speakerprofile)
    View toolbar_event;

    private Context mContext;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final int BACK_PRESS_DURATION = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_followers);
        ButterKnife.bind(this);
        intialiseUI();

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }


        PresenterInjector.injectFollowersPresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);
        setUpSwipeRefresh();

    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.speaker_followers_profile));
        mBack.setOnClickListener(this);
        mContext = getApplicationContext();
        refreshBtn.setOnClickListener(this);

        //RecyclerView
        mFollowersRecyclerView = findViewById(R.id.recyclerview_followers);
        mFollowersRecyclerView.setHasFixedSize(true);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 2);
        mFollowersRecyclerView.setLayoutManager(linearLayoutManager);

        mFollowersAdapter = new FollowersAdapter(this, this);
        mFollowersRecyclerView.setAdapter(mFollowersAdapter);


    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.refresh_followerscreen);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent,
                R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.start(FollowersActivity.this.getIntent().getExtras());

                swipeRefreshLayout.setRefreshing(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mFollowersAdapter != null) {
                            mFollowersAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, BACK_PRESS_DURATION);
            }
        });
    }

    @Override
    public void loadFollowers(List<Followers> followers) {
        if (followers.size() == 0) {
            try {
                noFollowersDialog(this).show();
            } catch (WindowManager.BadTokenException e) {
                //use a log message
            }
        }else {
            mFollowersRecyclerView.setVisibility(View.VISIBLE);
            mFollowersAdapter.loadFollowers(followers);
        }
    }

    @Override
    public void navigateToFollowersProfile(Followers followers) {
        Intent followersDetailsIntent = new Intent(this, FollowersDetailsActivity.class);
        followersDetailsIntent.putExtra(FollowersDetailsContract.KEY_FOLLOWERS_ID, followers.getmKey());
        startActivity(followersDetailsIntent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.anim_nothing);
    }

    @Override
    public void setPresenter(FollowersContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        //Show loading here
        Log.d(TAG,"It is Loading");
    }

    @Override
    public void hideLoading() {
        //Hide loading here
        Log.d(TAG,"It Loading stopped");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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


    private void checkInternet() {
        if (NetworkUtils.connectionStatus(this)) {
            mCard.setVisibility(View.VISIBLE);
            toolbar_event.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        mCard.setVisibility(View.INVISIBLE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @Override
    public void onFollowersClicked(Followers followers) {
        mPresenter.onFollowersClicked(followers);
    }

    /* No Followers Dialog */
    private AlertDialog.Builder noFollowersDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(getResources().getString(R.string.follower_dialog_title));
        builder.setMessage(getResources().getString(R.string.follower_dialog_message));

        builder.setPositiveButton(getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        return builder;
    }

}
