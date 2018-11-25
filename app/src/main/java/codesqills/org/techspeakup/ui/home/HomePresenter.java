package codesqills.org.techspeakup.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;

/**
 * Created by kamalshree on 11/3/2018.
 */

public class HomePresenter implements HomeContract.Presenter{

    private HomeContract.View mView;
    private DataHandler mDataHandler;

    public HomePresenter(HomeContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        getProfileDetails();

    }

    @Override
    public void destroy() {
        this.mView = null;
        this.mDataHandler = null;
    }

    @Override
    public void getProfileDetails() {
        mView.loadMyUserName(mDataHandler.getUserName());
    }

    @Override
    public void handleUserProfile() {
        mView.displayUserProfile();
    }

    @Override
    public void handleEditProfile() {
        mView.displayEditProfile();
    }

    @Override
    public void handleUserEvent() {
        mView.displayEvent();
    }

    @Override
    public void handleUserFollowers() {
        mView.displayFollowers();
    }

    @Override
    public void handleNotification() {
        mView.displayNotification();
    }

    public void handleUserSpeakers() {
        mView.displaySpeaker();
    }
}
