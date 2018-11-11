package codesqills.org.techspeakup.ui.followers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.events.EventsContract;

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
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        mDataHandler.fetchFollowers(0, currentFirebaseUser.getUid(), new DataHandler.Callback<List<String>>() {
            @Override
            public void onResponse(final List<String> result) {
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
