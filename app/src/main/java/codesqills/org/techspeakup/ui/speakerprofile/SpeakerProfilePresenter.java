package codesqills.org.techspeakup.ui.speakerprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;

/**
 * Created by kamalshree on 11/4/2018.
 */

public class SpeakerProfilePresenter implements SpeakerProfileContract.Presenter {

    SpeakerProfileContract.View mView;
    private DataHandler mDataHandler;

    public SpeakerProfilePresenter(SpeakerProfileContract.View mView) {
        this.mView = mView;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        getUserName();
        getUserLocation();
        getUserJob();
        getUserTwitter();
        getUserLinkedin();
        getUserWebsite();
        getUserAbout();
        getProfileImage();
        getEventCount();
        getEventDetails();
    }

    @Override
    public void destroy() {
        this.mView = null;
    }

    @Override
    public void getProfileImage() {
        mView.loadProfileImage(mDataHandler.getUserPic());
    }

    @Override
    public void getUserName() {
        mView.loadUserName(mDataHandler.getUserName());
    }

    @Override
    public void getUserLocation() {mView.loadUserLocation(mDataHandler.getEditLocation());
    }

    @Override
    public void getUserJob() {
        mView.loadUserJob(mDataHandler.getEditJob());
    }

    @Override
    public void getUserTwitter() {
        mView.loadUserTwitter(mDataHandler.getEditTwitter());
    }

    @Override
    public void getUserLinkedin() {
        mView.loadUserLinkedin(mDataHandler.getEditLinkedin());
    }

    @Override
    public void getUserWebsite() {
        mView.loadUserWebsite(mDataHandler.getEditWebsite());
    }

    @Override
    public void getUserAbout() {
        mView.loadUserAbout(mDataHandler.getEditAboutMe());
    }

    @Override
    public void getEventCount() {
        mView.loadUserEventCount(mDataHandler.getEditEventCount());
    }

    @Override
    public void getEventDetails() {
        mView.loadUserEventDetails(mDataHandler.getEditEventDetails());
    }

}
