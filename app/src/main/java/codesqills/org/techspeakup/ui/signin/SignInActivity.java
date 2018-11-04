package codesqills.org.techspeakup.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.profile.ProfileActivity;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private SignInContract.Presenter mPresenter;
    private static final int RC_SIGN_IN = 9001;
    private Bundle extras;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    @BindView(R.id.pb_signin)
    LottieAnimationView mProgressBar;

    @BindView(R.id.google_signin_btn)
    com.google.android.gms.common.SignInButton signin_btn;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User has already signed in, navigate to home
            navigateToProfile();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGso);
        PresenterInjector.injectSignInPresenter(this);

        findViewById(R.id.google_signin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.handleLoginRequest();
            }
        });

        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
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
    public void showLoginSuccess() {
        Toast.makeText(this, getString(R.string.login_success_text), Toast.LENGTH_SHORT).show();
        navigateToProfile();
    }

    @Override
    public void showLoginFailure(int statusCode, String message) {
        Toast.makeText(this, getString(R.string.login_failure_text), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void startSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void navigateToProfile() {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        if (extras != null) {
            profileIntent.putExtras(extras);
        }
        startActivity(profileIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter.destroy();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            e.printStackTrace();
            mPresenter.handleLoginFailure(e.getStatusCode(), e.getMessage());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        mPresenter.handleLoginSuccess(user.getEmail(), user.getDisplayName()
                                , user.getPhotoUrl());
                    } else {
                        mPresenter.handleLoginFailure(0, getString(R.string.unable_to_login));
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    mPresenter.handleLoginFailure(0, getString(R.string.login_failure_text));
                }
            }
        });

    }
}


