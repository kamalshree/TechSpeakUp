package codesqills.org.techspeakup.ui.profile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 10/29/2018.
 */

public class ProfilePresenter implements ProfileContract.Presenter {


    private ProfileContract.View mView;
    private DataHandler mDataHandler;

    public ProfilePresenter(ProfileContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }


    @Override
    public void start(@Nullable Bundle extras) {
        if (mDataHandler.isLoggedIn()) {
            // If user is logged in directly navigate to home
            mView.onProfileSaved(mDataHandler.getUserType());
            return;
        }

        // Updating UI with data we have
        mView.loadEmailAddress(mDataHandler.getUserEmail());
        mView.loadUserName(mDataHandler.getUserName());
        mView.loadUserPic(mDataHandler.getUserPic());
        mView.loadUserType(mDataHandler.getUserType());

    }

    @Override
    public void destroy() {
        this.mView = null;
    }

    @Override
    public void saveProfile(@Nullable Bitmap picture, String userType) {

    }

    @Override
    public void saveProfile(@Nullable String pictureUrl, String username, final String userType) {
        if (pictureUrl != null && !pictureUrl.isEmpty()) {
            mDataHandler.saveUserPic(pictureUrl);
        }

        mDataHandler.saveUserType(userType);
        mDataHandler.saveUserName(username);

            mDataHandler.setUserInfo(new DataHandler.Callback<Void>() {
                @Override
                public void onResponse(Void result) {
                    mView.onProfileSaved(userType);
                }

                @Override
                public void onError() {
                    mView.onSaveError();
                }
            });




    }
}