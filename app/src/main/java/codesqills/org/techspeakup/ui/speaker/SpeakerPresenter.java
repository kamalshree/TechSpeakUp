package codesqills.org.techspeakup.ui.speaker;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.search.SearchContract;

/**
 * Created by kamalshree on 11/18/2018.
 */

public class SpeakerPresenter implements SpeakerContract.Presenter {

    private DataHandler mDataHandler;
    private SpeakerContract.View mView;
    private List<User> mUsers;


    public SpeakerPresenter(SpeakerContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();

        mUsers = new ArrayList<>();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        mDataHandler.fetchSpeakers(new DataHandler.Callback<List<User>>() {
            @Override
            public void onResponse(final List<User> result) {
                mView.loadSpeakerDetails(result);
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

    @Override
    public void onSpeakerClicked(User user) {
        mView.showRateDialog(user);

    }

    @Override
    public void onSpeakerFollowerClicked(User user) {
        mView.showFollowerDialog(user);

    }
}
