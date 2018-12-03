package codesqills.org.techspeakup.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.home.HomeActivity;
import codesqills.org.techspeakup.ui.userdashboard.UserDashboardActivity;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View,
            View.OnClickListener {

    private Button mBtnNext;

    private Bundle extras;
    private ProfileContract.Presenter mPresenter;

    @BindView(R.id.img_user_pic)
    ImageView mImgUserPic;

    @BindView(R.id.details_page_spinner_track)
    Spinner mUserType;

    @BindView(R.id.et_user_name)
    EditText mEtUserName;

    @BindView(R.id.tv_email_details)
    TextView mTvUserEmail;

    @BindView(R.id.pb_profile)
    ProgressBar mProgressBar;

    String deviceid;
    NetworkUtils networkUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ButterKnife.bind(this);
        check_connection();
        initializeUI();
        // Injecting presenter
        PresenterInjector.injectProfilePresenter(this);

        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    /* Check Internet Connection */
    public void check_connection(){
        networkUtils=new NetworkUtils(this);
        networkUtils.execute();
    }

    private void initializeUI() {
        mBtnNext = findViewById(R.id.btn_details_proceed);
        mBtnNext.setOnClickListener(this);

        mProgressBar.setIndeterminate(true);
    }

    @Override
    public void loadUserPic(String picUrl) {
        if (picUrl == null) {
            return;
        }
        Glide.with(getApplicationContext()).load(picUrl).apply(RequestOptions.circleCropTransform()).into(mImgUserPic);
    }

    @Override
    public void loadUserName(String userName) {
        if (userName == null) {
            return;
        }
        mEtUserName.setText(userName);
    }

    @Override
    public void loadEmailAddress(String emailAddress) {
        mTvUserEmail.setText(emailAddress);
    }

    @Override
    public void loadUserType(String userType) {
        if (userType == null) {
            return;
        }
    }

    @Override
    public void onPictureUploadError() {
        Toast.makeText(this, getString(R.string.error_uploading_picture), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileSaved(String userType) {
       //If "Speaker" type route to speaker dashboard else User dashboard
        if(userType.equals("Speaker")){
            Intent homeIntent = new Intent(this, HomeActivity.class);
            if (extras != null) {
                homeIntent.putExtras(extras);
            }
            startActivity(homeIntent);
            this.finish();
        }
        else{
            Intent userIntent = new Intent(this, UserDashboardActivity.class);
            if (extras != null) {
                userIntent.putExtras(extras);
            }
            startActivity(userIntent);
            this.finish();
        }

    }



    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
        public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_details_proceed:
                if (mEtUserName.getText().toString().trim().equals("")) {
                    mEtUserName.setError(getResources().getString(R.string.user_name_empty_error));
                } else if (mUserType.getSelectedItem().toString().trim().equals(getResources().getString(R.string.select_your_Type))) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_your_Type), Toast.LENGTH_LONG).show();
                } else {
                    mPresenter.saveProfile("", mEtUserName.getText().toString(),
                            mUserType.getSelectedItem().toString().trim());
                }

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
}
