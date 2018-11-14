package codesqills.org.techspeakup.ui.notificationsend;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsContract;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NotificationSendActivity extends AppCompatActivity implements NotificationSendContract.View,View.OnClickListener{

    private NotificationSendContract.Presenter mPresenter;
    private Bundle extras;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;
    @BindView(R.id.refresh)
    Button refreshBtn;

    @BindView(R.id.et_notification_send_name)
    TextView showToName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_send);
        ButterKnife.bind(this);
        extras = getIntent().getExtras();
        PresenterInjector.injectNotificationSendPresenter(this);
        intialiseUI();
        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }

        mPresenter.start(getIntent().getExtras());
    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.speaker_event_details));
        String toName = extras.getString(NotificationSendContract.KEY_FOLLOWER_SEND_NAME);
        showToName.setText(toName);
        mBack.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }


    @Override
    public void setPresenter(NotificationSendContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
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
}

