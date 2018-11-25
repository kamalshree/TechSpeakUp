package codesqills.org.techspeakup.ui.followersdetails;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileActivity;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class FollowersDetailsActivity extends AppCompatActivity implements FollowersDetailsContract.View,View.OnClickListener {
    private static final String TAG = "FollowersDetails";


    @BindView(R.id.refresh)
    Button refreshBtn;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.linear_layout_main)
    LinearLayout mLinearLayout;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;

    private Bundle extras;

    @BindView(R.id.speaker_followerdetails_tv_name)
    TextView followerdetails_tv_name;

    @BindView(R.id.speaker_followerdetails_tv_designation)
    TextView followerdetails_tv_designation;

    @BindView(R.id.speaker_followerdetails_tv_map_val)
    TextView followerdetails_tv_map_val;

    @BindView(R.id.tv_followerdetails_twitter_val)
    TextView followerdetails_twitter_val;

    @BindView(R.id.iv_followerdetails_linkedin_val)
    TextView followerdetails_linkedin_val;

    @BindView(R.id.iv_followerdetails_link_val)
    TextView followerdetails_link_val;

    @BindView(R.id.tv_followerdetails_about_val)
    TextView followerdetails_about_val;

    @BindView(R.id.speaker_followerdetails_iv)
    ImageView followerdetails_pic;


    private FollowersDetailsContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_followersdetails);
        ButterKnife.bind(this);
        intialiseUI();
        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }

        PresenterInjector.injectFollowersDetailsPresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);
        checkNewProfile();
    }

    private void intialiseUI() {
        editProfile.setText("Profile");
        mBack.setOnClickListener(this);
    }


    private void checkNewProfile() {
        if (followerdetails_tv_map_val.getText().toString().matches("") && followerdetails_twitter_val.getText().toString().matches("") && followerdetails_linkedin_val.getText().toString().matches("") &&
                followerdetails_link_val.getText().toString().matches("") && followerdetails_about_val.getText().toString().matches("")) {
            buildEmptyDialog(this).show();
        }

    }

    @Override
    public void setPresenter(FollowersDetailsContract.Presenter presenter) {
            this.mPresenter=presenter;
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
            mLinearLayout.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        mLinearLayout.setVisibility(View.INVISIBLE);
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
    public void loadFollowertPic(String followerPic) {
        if (followerPic == null) {
            return;
        }
        Glide.with(getApplicationContext()).load(followerPic).apply(RequestOptions.circleCropTransform()).into(followerdetails_pic);

    }

    @Override
    public void loadFollowertName(String followerName) {
        if (followerName == null) {
            return;
        }
        followerdetails_tv_name.setText(followerName);
    }

    @Override
    public void loadFollowertJob(String followerJob) {
        if (followerJob == null) {
            return;
        }
        followerdetails_tv_designation.setText(followerJob);
    }

    @Override
    public void loadFollowertLocation(String followerLocation) {
        if (followerLocation == null) {
            return;
        }
        followerdetails_tv_map_val.setText(followerLocation);
    }

    @Override
    public void loadFollowertTwitter(String followerTwitter) {
        if (followerTwitter == null) {
            return;
        }
        followerdetails_twitter_val.setText(followerTwitter);
    }

    @Override
    public void loadFollowertLinkedin(String followerLinkedin) {
        if (followerLinkedin == null) {
            return;
        }
        followerdetails_linkedin_val.setText(followerLinkedin);

    }

    @Override
    public void loadFollowertLink(String followerLink) {
        if (followerLink == null) {
            return;
        }
        followerdetails_link_val.setText(followerLink);
    }

    @Override
    public void loadFollowertAbout(String followerAbout) {
        if (followerAbout == null) {
            return;
        }
        followerdetails_about_val.setText(followerAbout);
    }


    /* Profile not updated Dialog */
    private AlertDialog.Builder buildEmptyDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(getResources().getString(R.string.speaker_profile_dialog_title));
        builder.setMessage(getResources().getString(R.string.speaker_profile_dialog_message));

        builder.setPositiveButton(getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent editprofile = new Intent(getApplication(), SpeakerEditProfileActivity.class);
                startActivity(editprofile);
            }

        });

        return builder;
    }


}
