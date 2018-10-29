package codesqills.org.techspeakup.ui.signin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.DataHandler;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;
    private DataHandler mDataHandler;
    private String userType;
    private Button signin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userType = getIntent().getStringExtra("userType");

        signin_btn = (Button) findViewById(R.id.signin_btn);
        if (userType.equals("speaker")) {
            signin_btn.setText(getResources().getString(R.string.signin_btn_speaker));
        } else {
            signin_btn.setText(getResources().getString(R.string.signin_btn_user));
        }
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoginSuccess() {

    }

    @Override
    public void showLoginFailure() {

    }
}
