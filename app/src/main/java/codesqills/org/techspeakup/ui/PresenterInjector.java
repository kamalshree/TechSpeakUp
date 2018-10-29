package codesqills.org.techspeakup.ui;

import codesqills.org.techspeakup.ui.register.RegisterContract;
import codesqills.org.techspeakup.ui.register.RegisterPresenter;
import codesqills.org.techspeakup.ui.signin.SignInContract;
import codesqills.org.techspeakup.ui.signin.SignInPresenter;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class PresenterInjector {

    public static void injectRegisterPresenter(RegisterContract.View registerView) {
       new RegisterPresenter(registerView);
    }
}
