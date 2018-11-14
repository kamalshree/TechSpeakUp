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

public class NotificationFollowersActivity extends AppCompatActivity implements NotificationFollowersAdapter.NotificationItemListener,NotificationFollowersContract.View, View.OnClickListener {

    private NotificationFollowersContract.Presenter mPresenter;
    private Bundle extras;
    private NotificationFollowersAdapter mFollowersAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;
    @BindView(R.id.tv_no_internet)
    TextView noInternet;
    @BindView(R.id.refresh)
    Button refreshBtn;
    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.recyclerview_notification_followers)
    RecyclerView mFollowerRecyclerView;


    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;

    @BindView(R.id.layout_internet)
    View layout_internet;

    private static final int BACK_PRESS_DURATION = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_follower);
        ButterKnife.bind(this);
        intialiseUI();

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }


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

        mFollowersAdapter = new NotificationFollowersAdapter( this);
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

    }

    @Override
    public void hideLoading() {

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
