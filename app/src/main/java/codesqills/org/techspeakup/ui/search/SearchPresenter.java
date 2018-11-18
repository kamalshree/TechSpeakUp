package codesqills.org.techspeakup.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.notificationFollowers.NotificationFollowersContract;

/**
 * Created by kamalshree on 11/17/2018.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private DataHandler mDataHandler;
    private SearchContract.View mView;
    private List<User> mUsers;

    public SearchPresenter(SearchContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();

        mUsers = new ArrayList<>();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        mDataHandler.fetchAllUsers(0, new DataHandler.Callback<List<User>>() {
            @Override
            public void onResponse(List<User> result) {
                mUsers.clear();
                mUsers.addAll(result);
                mView.loadUserDetails(result);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void destroy() {
        this.mView = null;
        this.mDataHandler = null;
    }
}
