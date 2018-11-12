package codesqills.org.techspeakup.ui.followers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Followers;

/**
 * Created by kamalshree on 11/9/2018.
 */

public class FollowersPresenter implements FollowersContract.Presenter {

    FollowersContract.View mView;
    private DataHandler mDataHandler;


    public FollowersPresenter(FollowersContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

        mDataHandler.fetchFollowers(new DataHandler.Callback<List<Followers>>() {
            @Override
            public void onResponse(final List<Followers> result) {
                mView.loadFollowers(result);
            }

            @Override
            public void onError() {
                //
            }
        });
    }

    @Override
    public void destroy() {
        this.mView = null;
        this.mDataHandler = null;
    }
}
