package codesqills.org.techspeakup.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.home.HomeContract;

/**
 * Created by kamalshree on 11/21/2018.
 */

public class MapPresenter implements MapContract.Presenter{

    private MapContract.View mView;
    private DataHandler mDataHandler;
    private List<User> mUsers;

    public MapPresenter(MapContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        mUsers = new ArrayList<>();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        mDataHandler.fetchAllUsersMap(0, new DataHandler.Callback<List<User>>() {
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
