package codesqills.org.techspeakup.ui.notificationFollowers;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.message.MessageDialog;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NotificationFollowersActivity extends AppCompatActivity implements NotificationFollowersAdapter.NotificationItemListener, NotificationFollowersContract.View, View.OnClickListener {
    private static final String TAG = "NotificationFollowers";

    private NotificationFollowersContract.Presenter mPresenter;
    private Bundle extras;
    private NotificationFollowersAdapter mFollowersAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;
    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.recyclerview_notification_followers)
    RecyclerView mFollowerRecyclerView;


    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;


    private static final int BACK_PRESS_DURATION = 3000;
    NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_follower);
        ButterKnife.bind(this);
        intialiseUI();
        check_connection();

        PresenterInjector.injectNotificationFollowersPresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);
        setUpSwipeRefresh();


        //RecyclerView
        mFollowerRecyclerView = findViewById(R.id.recyclerview_notification_followers);
        mFollowerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mFollowerRecyclerView.setLayoutManager(linearLayoutManager);

        mFollowersAdapter = new NotificationFollowersAdapter(this);
        mFollowerRecyclerView.setAdapter(mFollowersAdapter);
    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.refresh_followerscreen);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent,
                R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.start(NotificationFollowersActivity.this.getIntent().getExtras());

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

    /* Check Internet Connection */
    public void check_connection() {
        networkUtils = new NetworkUtils(this);
        networkUtils.execute();
    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.new_notification_profile));
        mBack.setOnClickListener(this);

    }

    @Override
    public void setPresenter(NotificationFollowersContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        //Show loading here
        Log.d(TAG, "It is Loading");
    }

    @Override
    public void hideLoading() {
        //Hide loading here
        Log.d(TAG, "It Loading stopped");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speaker_profile_page_back:
                onBackPressed();
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

    @Override
    public void loadFollowers(List<User> followers) {
        mFollowerRecyclerView.setVisibility(View.VISIBLE);
        mFollowersAdapter.loadFollowers(followers);
    }

    /* Open Notificiation Dialog */
    @Override
    public void navigateToNotificationDetails(User users) {
        MessageDialog dialog = new MessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_user_id), users.getKey());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_message));
    }

    @Override
    public void loadFollowersError() {
        //show error
    }

    @Override
    public void onFollowersClicked(User followers) {
        mPresenter.onFollowersClicked(followers);
    }
}
