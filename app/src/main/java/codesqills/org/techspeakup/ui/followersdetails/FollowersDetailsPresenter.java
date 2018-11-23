package codesqills.org.techspeakup.ui.followersdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsContract;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class FollowersDetailsPresenter implements FollowersDetailsContract.Presenter{

    private DataHandler mDataHandler;
    private FollowersDetailsContract.View mView;
    private String mFollowerID;

    public FollowersDetailsPresenter(FollowersDetailsContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }


    @Override
    public void start(@Nullable Bundle extras) {

        getUserLocation();
        getUserJob();
        getUserTwitter();
        getUserLinkedin();
        getUserWebsite();
        getUserAbout();

        String followerId = extras.getString(FollowersDetailsContract.KEY_FOLLOWERS_ID);
        this.mFollowerID = followerId;

        mDataHandler.fetchFollowersById(followerId, new DataHandler.Callback<User>() {
            @Override
            public void onResponse(User result) {

                mView.loadFollowertPic(result.getImage());
                mView.loadFollowertName(result.getName());
                mView.loadFollowertJob(result.getJob());
                mView.loadFollowertLocation(result.getLocation());
                mView.loadFollowertTwitter(result.getTwitter());
                mView.loadFollowertLinkedin(result.getLinkedin());
                mView.loadFollowertLink(result.getWebsite());
                mView.loadFollowertAbout(result.getAbout());

            }

            @Override
            public void onError() {
                mView.hideLoading();
            }
        });


    }

    @Override
    public void getUserLocation() {mView.loadFollowertLocation(mDataHandler.getEditLocation());
    }

    @Override
    public void getUserJob() {
        mView.loadFollowertJob(mDataHandler.getEditJob());
    }

    @Override
    public void getUserTwitter() {
        mView.loadFollowertTwitter(mDataHandler.getEditTwitter());
    }

    @Override
    public void getUserLinkedin() {
        mView.loadFollowertLinkedin(mDataHandler.getEditLinkedin());
    }

    @Override
    public void getUserWebsite() {
        mView.loadFollowertLink(mDataHandler.getEditWebsite());
    }

    @Override
    public void getUserAbout() {
        mView.loadFollowertAbout(mDataHandler.getEditAboutMe());
    }

    @Override
    public void destroy() {
        this.mView=null;
        this.mDataHandler=null;
    }
}
