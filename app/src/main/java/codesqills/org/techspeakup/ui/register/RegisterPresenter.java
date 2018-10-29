package codesqills.org.techspeakup.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.ui.signin.SignInContract;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;


    public RegisterPresenter(RegisterContract.View view) {
        this.mView = view;
        view.setPresenter(this);
    }


    @Override
    public void start(@Nullable Bundle extras) {
        //
    }

    @Override
    public void destroy() {
        this.mView = null;
    }

    @Override
    public void handleSpeakerRegistration(String userType) {
        mView.hideLoading();
        mView.displaySignInScreen(userType);
        mView.displaySignInSuccess(userType);
    }

    @Override
    public void handleUserRegistration(String userType) {
        mView.hideLoading();
        mView.displaySignInScreen(userType);
        mView.displaySignInSuccess(userType);
    }
}
