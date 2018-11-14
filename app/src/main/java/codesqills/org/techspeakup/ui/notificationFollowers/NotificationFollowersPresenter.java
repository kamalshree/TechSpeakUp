package codesqills.org.techspeakup.ui.notificationFollowers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NotificationFollowersPresenter implements NotificationFollowersContract.Presenter {

    private DataHandler mDataHandler;
    private NotificationFollowersContract.View mview;
    private List<User> mFollowers;

    public NotificationFollowersPresenter(NotificationFollowersContract.View view) {
        this.mview = view;
        this.mDataHandler = DataHandlerProvider.provide();

        mFollowers = new ArrayList<>();
        this.mview.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        mview.showLoading();
        mDataHandler.fetchAllUsers(0, new DataHandler.Callback<List<User>>() {
            @Override
            public void onResponse(List<User> result) {
                mFollowers.clear();
                mFollowers.addAll(result);
                mview.loadFollowers(result);
                mview.hideLoading();

            }

            @Override
            public void onError() {
                mview.loadFollowersError();
                mview.hideLoading();
            }
        });
    }

    @Override
    public void destroy() {
        this.mview = null;
        this.mDataHandler = null;
    }

    @Override
    public void onFollowersClicked(User user) {
        mview.navigateToNotificationDetails(user);
    }
}
