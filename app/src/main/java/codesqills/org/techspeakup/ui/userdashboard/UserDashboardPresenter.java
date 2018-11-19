package codesqills.org.techspeakup.ui.userdashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Events;

/**
 * Created by kamalshree on 11/16/2018.
 */

public class UserDashboardPresenter implements UserDashboardContract.Presenter {
    private UserDashboardContract.View mView;
    private DataHandler mDataHandler;
    private List<Events> mEvents;

    public UserDashboardPresenter(UserDashboardContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        mEvents = new ArrayList<>();

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
    public void handleSpeakers() {
        mView.displaySpeakers();
    }

    @Override
    public void handleNotification() {
        mView.displayNotification();
    }
}
