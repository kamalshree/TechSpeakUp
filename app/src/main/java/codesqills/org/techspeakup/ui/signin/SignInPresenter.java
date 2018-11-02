package codesqills.org.techspeakup.ui.signin;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class SignInPresenter implements SignInContract.Presenter {

    private SignInContract.View mView;
    private DataHandler mDataHandler;

    @Override
    public void start(@Nullable Bundle extras) {
    //
    }

    @Override
    public void destroy() {
        this.mView = null;
    }

        public SignInPresenter(SignInContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        view.setPresenter(this);
    }

    @Override
    public void handleLoginRequest() {
        mView.showLoading();
        mView.startSignIn();
    }

    @Override
    public void handleLoginSuccess(String email, String displayName, Uri photoUrl) {
        mDataHandler.saveUserEmail(email);
        mDataHandler.saveUserName(displayName);
        mDataHandler.saveUserPic(photoUrl.toString());
        mView.hideLoading();
        mView.showLoginSuccess();
    }

    @Override
    public void handleLoginFailure(int statusCode, String message) {
        mView.hideLoading();
        mView.showLoginFailure(statusCode, message);
    }

}
