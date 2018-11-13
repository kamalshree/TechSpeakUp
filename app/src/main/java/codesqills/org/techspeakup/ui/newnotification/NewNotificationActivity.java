package codesqills.org.techspeakup.ui.newnotification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.notificationFollowers.NotificationFollowersActivity;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NewNotificationActivity extends AppCompatActivity implements NewNotificationContract.View, View.OnClickListener {
    private NewNotificationContract.Presenter mPresenter;
    private Bundle extras;

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

    @BindView(R.id.fab_notificaton)
    FloatingActionButton fab_notification;

    @BindView(R.id.layout_internet)
    View layout_internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notification);
        ButterKnife.bind(this);
        intialiseUI();

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }


        PresenterInjector.injectNewNotificationPresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.new_notification_profile));
        mBack.setOnClickListener(this);
        fab_notification.setOnClickListener(this);

    }

    @Override
    public void setPresenter(NewNotificationContract.Presenter presenter) {
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
            case R.id.fab_notificaton:
                mPresenter.openNewNotification();
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
    public void navigateToNotificationFollowers() {
        Intent notifctaionFollowersIntent = new Intent(this, NotificationFollowersActivity.class);
        startActivity(notifctaionFollowersIntent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.anim_nothing);
    }
}
