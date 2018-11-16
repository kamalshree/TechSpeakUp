package codesqills.org.techspeakup.ui.newnotification;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Message;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NewNotificationPresenter implements NewNotificationContract.Presenter {

    NewNotificationContract.View mView;
    private DataHandler mDataHandler;


    public NewNotificationPresenter(NewNotificationContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        mDataHandler.fetchNotifications(new DataHandler.Callback<List<Message>>() {
            @Override
            public void onResponse(final List<Message> result) {
                mView.loadNotifications(result);
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
        this.mDataHandler=null;
    }

    @Override
    public void openNewNotification() {
        mView.navigateToNotificationFollowers();
    }

    @Override
    public void onNotificationClicked(Message message) {
        mView.deleteNotifications(message);
    }
}
