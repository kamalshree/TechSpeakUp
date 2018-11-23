package codesqills.org.techspeakup.ui.editprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;

/**
 * Created by kamalshree on 11/5/2018.
 */

public class SpeakerEditProfilePresenter implements SpeakerEditProfileContract.Presenter {

    SpeakerEditProfileContract.View mView;
    private DataHandler mDataHandler;

    public SpeakerEditProfilePresenter(SpeakerEditProfileContract.View mView) {
        this.mView = mView;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

        getEditName();
        getEditLocation();
        getEditJob();
        getEditTwitter();
        getEditLinkedin();
        getEditWebsite();
        getEditAbout();
        getEditEventCount();
        getEditEventDetails();

    }

    @Override
    public void destroy() {
       this.mView=null;
    }

    @Override
    public void getEditName() {
        mView.setEditName(mDataHandler.getUserName());
    }

    @Override
    public void getEditLocation() {
        mView.setEditLocation(mDataHandler.getEditLocation());
    }

    @Override
    public void getEditJob() {
        mView.setEditJob(mDataHandler.getEditJob());
    }

    @Override
    public void getEditTwitter() {
        mView.setEditTwitter(mDataHandler.getEditTwitter());
    }

    @Override
    public void getEditLinkedin() {
        mView.setEditLinkedin(mDataHandler.getEditLinkedin());
    }

    @Override
    public void getEditWebsite() {
        mView.setEditWebsite(mDataHandler.getEditWebsite());
    }

    @Override
    public void getEditAbout() {
        mView.setEditAbout(mDataHandler.getEditAboutMe());
    }

    @Override
    public void getEditEventCount() {
        mView.setEditEventCount(mDataHandler.getEditEventCount());
    }

    @Override
    public void getEditEventDetails() {
        mView.setEditEventDetails(mDataHandler.getEditEventDetails());
    }



    @Override
    public void saveEditProfile(@Nullable String editName, String editLocation,String editJob, String editTwitter, String editLinkedin, String editWebsite, String editAboutMe,String FollowerCount,String RateCount,String editEventCount,String editEventDetails) {

        mDataHandler.saveEditName(editName);
        mDataHandler.saveEditLocation(editLocation);
        mDataHandler.saveEditJob(editJob);
        mDataHandler.saveEditTwitter(editTwitter);
        mDataHandler.saveEditLinkedin(editLinkedin);
        mDataHandler.saveEditWebsite(editWebsite);
        mDataHandler.saveEditAboutMe(editAboutMe);
        mDataHandler.saveFollowerCount(FollowerCount);
        mDataHandler.saveRateCount(RateCount);
        mDataHandler.saveEditEventCount(editEventCount);
        mDataHandler.saveEditEventDetails(editEventDetails);

        mDataHandler.setUserInfo(new DataHandler.Callback<Void>() {
            @Override
            public void onResponse(Void result) {
                mView.onProfileSaved();
            }

            @Override
            public void onError() {
                mView.onSaveError();
            }
        });
    }
}
