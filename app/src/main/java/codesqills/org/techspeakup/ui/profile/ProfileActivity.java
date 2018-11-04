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

import codesqills.org.techspeakup.NavigationDrawerActivity;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.home.HomeActivity;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View,
        View.OnClickListener {

    private ImageView mImgUserPic;
    private Spinner mUserType;
    private EditText mEtUserName;
    private TextView mTvUserEmail;
    private ProgressBar mProgressBar;
    private Button mBtnNext;

    private Bundle extras;
    private ProfileContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        initializeUI();

        // Injecting presenter
        PresenterInjector.injectProfilePresenter(this);

        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    private void initializeUI() {
        mImgUserPic = findViewById(R.id.img_user_pic);
        mUserType = findViewById(R.id.details_page_spinner_track);
        mEtUserName = findViewById(R.id.et_user_name);
        mTvUserEmail = findViewById(R.id.tv_email_details);

        mBtnNext = findViewById(R.id.btn_details_proceed);
        mBtnNext.setOnClickListener(this);

        mProgressBar = findViewById(R.id.pb_profile);
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
    public void onProfileSaved() {
        Toast.makeText(this, getString(R.string.profile_saved_successfully), Toast.LENGTH_SHORT).show();
        // Navigate to home activity
        Intent homeIntent = new Intent(this, HomeActivity.class);
        if (extras != null) {
            homeIntent.putExtras(extras);
        }
        startActivity(homeIntent);
        this.finish();
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
                } else if (mImgUserPic.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_image_empty), Toast.LENGTH_LONG).show();
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
