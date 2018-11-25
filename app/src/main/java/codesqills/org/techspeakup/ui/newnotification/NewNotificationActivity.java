package codesqills.org.techspeakup.ui.newnotification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import codesqills.org.techspeakup.data.models.Message;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.message.DeleteMessageDialog;
import codesqills.org.techspeakup.ui.notificationFollowers.NotificationFollowersActivity;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NewNotificationActivity extends AppCompatActivity implements NewNotificationAdapter.NotificationsItemListener, NewNotificationContract.View, View.OnClickListener {
    private static final String TAG = "NewNotificationActivity";


    private NewNotificationContract.Presenter mPresenter;
    private Bundle extras;

    private RecyclerView mNotificationRecyclerView;
    private NewNotificationAdapter mNotificationAdapter;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;

    @BindView(R.id.fab_notificaton)
    FloatingActionButton fab_notification;


    private Context mContext;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final int BACK_PRESS_DURATION = 3000;
    NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notification);
        ButterKnife.bind(this);
        intialiseUI();
        check_connection();


        PresenterInjector.injectNewNotificationPresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);

        setUpSwipeRefresh();

    }

    /* Check Internet Connection */
    public void check_connection() {
        networkUtils = new NetworkUtils(this);
        networkUtils.execute();
    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.new_notification_profile));
        mBack.setOnClickListener(this);
        fab_notification.setOnClickListener(this);

        //RecyclerView
        mNotificationRecyclerView = findViewById(R.id.recyclerview_notification);
        mNotificationRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mNotificationRecyclerView.setLayoutManager(linearLayoutManager);

        mNotificationAdapter = new NewNotificationAdapter(this, this);
        mNotificationRecyclerView.setAdapter(mNotificationAdapter);

    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.refresh_notificationscreen);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent,
                R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.start(NewNotificationActivity.this.getIntent().getExtras());

                swipeRefreshLayout.setRefreshing(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mNotificationAdapter != null) {
                            mNotificationAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, BACK_PRESS_DURATION);
            }
        });
    }

    @Override
    public void loadNotifications(List<Message> messages) {
        if (messages.size() == 0) {
            try {
                noNotificationsDialog(this).show();
            } catch (WindowManager.BadTokenException e) {
                //use a log message
            }
        } else {
            mNotificationRecyclerView.setVisibility(View.VISIBLE);
            mNotificationAdapter.loadFollowers(messages);
        }
    }

    @Override
    public void setPresenter(NewNotificationContract.Presenter presenter) {
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
            case R.id.fab_notificaton:
                mPresenter.openNewNotification();
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
    public void navigateToNotificationFollowers() {
        Intent notifctaionFollowersIntent = new Intent(this, NotificationFollowersActivity.class);
        startActivity(notifctaionFollowersIntent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.anim_nothing);
    }

    @Override
    public void onNotificationClicked(Message messages) {

        mPresenter.onNotificationClicked(messages);

    }

    public void deleteNotifications(Message messages) {
        DeleteMessageDialog dialog = new DeleteMessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_message_id), messages.getKey());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_message));

    }

    /* No Notification Dialog */
    private AlertDialog.Builder noNotificationsDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(getResources().getString(R.string.notification_dialog_title));
        builder.setMessage(getResources().getString(R.string.notification_dialog_message));

        builder.setPositiveButton(getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        return builder;
    }
}
