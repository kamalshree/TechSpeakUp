package codesqills.org.techspeakup.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.signin.SignInActivity;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;
    private LottieAnimationView mProgressBar;

    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        PresenterInjector.injectRegisterPresenter(this);
        mProgressBar = findViewById(R.id.pb_loading);

        findViewById(R.id.signin_btn_speaker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.handleSpeakerRegistration("speaker");
            }
        });

        findViewById(R.id.signin_btn_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.handleUserRegistration("user");
            }
        });

        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, getString(R.string.loding_text), Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void displaySignInScreen(String userType) {
        Intent signInIntent = new Intent(this, SignInActivity.class);
        signInIntent.putExtra("userType", userType);
        startActivity(signInIntent);
    }

    @Override
    public void displaySignInSuccess(String userType) {
        Toast.makeText(this, "Welcome to Sign In screen :"+userType, Toast.LENGTH_SHORT).show();
    }
}
