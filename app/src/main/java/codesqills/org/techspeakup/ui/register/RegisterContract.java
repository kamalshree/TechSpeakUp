package codesqills.org.techspeakup.ui.register;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 10/25/2018.
 */

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void displaySignInScreen(String userType);
        void displaySignInSuccess(String userType);
    }

    interface Presenter extends BasePresenter{
        void handleSpeakerRegistration(String userType);
        void handleUserRegistration(String userType);

    }
}
