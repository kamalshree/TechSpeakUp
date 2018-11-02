package codesqills.org.techspeakup.ui.signin;

import android.net.Uri;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 10/25/2018.
 */

public interface SignInContract {

    interface View extends BaseView<Presenter> {
        void showLoginSuccess();

        void showLoginFailure(int statusCode, String message);

        void startSignIn();

        void navigateToProfile();
    }

    interface Presenter extends BasePresenter {
        void handleLoginRequest();

        void handleLoginSuccess(String email, String displayName, Uri photoUrl);

        void handleLoginFailure(int statusCode, String message);
    }
}
