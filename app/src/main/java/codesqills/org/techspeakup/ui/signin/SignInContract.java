package codesqills.org.techspeakup.ui.signin;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 10/25/2018.
 */

public interface SignInContract {

    interface View extends BaseView<Presenter> {
            void showLoginSuccess();
            void showLoginFailure();
    }

    interface Presenter extends BasePresenter {
        void handleLoginRequest();
        void handleLoginSuccess();
        void handleLoginFailure();
    }
}
