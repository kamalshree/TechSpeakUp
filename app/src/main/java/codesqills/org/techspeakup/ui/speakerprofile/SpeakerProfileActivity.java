package codesqills.org.techspeakup.ui.speakerprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/4/2018.
 */

public class SpeakerProfileActivity extends AppCompatActivity implements SpeakerProfileContract.View, View.OnClickListener {

    private SpeakerProfileContract.Presenter mPresenter;

    @BindView(R.id.speaker_profile_tv_name)
    TextView mUsername;

    @BindView(R.id.speaker_profile_tv_map_val)
    TextView mUserlocation;

    @BindView(R.id.speaker_profile_tv_designation)
    TextView mUserjob;

    @BindView(R.id.tv_twitter_val)
    TextView mUsertwitter;

    @BindView(R.id.iv_linkedin_val)
    TextView mUserlinkedin;

    @BindView(R.id.iv_link_val)
    TextView mUserlink;

    @BindView(R.id.tv_about_val)
    TextView mUserabout;

    @BindView(R.id.speaker_profile_iv)
    ImageView mProfileImage;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    private Bundle extras;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;

    @BindView(R.id.refresh)
    Button refreshBtn;

    @BindView(R.id.relative_layout)
    RelativeLayout relative_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_profile);
        ButterKnife.bind(this);

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }
        intialiseUI();
        PresenterInjector.injectSpeakerProfilePresenter(this);

        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    private void intialiseUI() {
        mBack.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }
    @Override
    public void setPresenter(SpeakerProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadProfileImage(String picUrl) {
        if (picUrl == null) {
            return;
        }
        Glide.with(getApplicationContext()).load(picUrl).apply(RequestOptions.circleCropTransform()).into(mProfileImage);
    }

    @Override
    public void loadUserName(String userName) {
        mUsername.setText(userName);
    }

    @Override
    public void loadUserLocation(String location) {
        mUserlocation.setText(location);
    }

    @Override
    public void loadUserJob(String job) {
        mUserjob.setText(job);
    }

    @Override
    public void loadUserTwitter(String twiiter) {
        mUsertwitter.setText(twiiter);

    }

    @Override
    public void loadUserLinkedin(String linkedin) {
        mUserlinkedin.setText(linkedin);
    }

    @Override
    public void loadUserWebsite(String website) {
        mUserlink.setText(website);

    }

    @Override
    public void loadUserAbout(String about) {
        mUserabout.setText(about);
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
            relative_layout.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        relative_layout.setVisibility(View.INVISIBLE);
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
