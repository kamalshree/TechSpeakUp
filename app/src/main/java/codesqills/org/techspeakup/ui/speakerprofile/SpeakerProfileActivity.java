package codesqills.org.techspeakup.ui.speakerprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_profile);
        ButterKnife.bind(this);
        PresenterInjector.injectSpeakerProfilePresenter(this);

        mBack.setOnClickListener(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);
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
        onBackPressed();
    }
}
