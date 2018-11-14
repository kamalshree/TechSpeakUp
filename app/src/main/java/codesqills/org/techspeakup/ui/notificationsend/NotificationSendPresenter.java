package codesqills.org.techspeakup.ui.notificationsend;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.ui.notificationFollowers.NotificationFollowersContract;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NotificationSendPresenter implements NotificationSendContract.Presenter {
    private DataHandler mDataHandler;
    private NotificationSendContract.View mview;


    public NotificationSendPresenter(NotificationSendContract.View view) {
        this.mview = view;
        this.mDataHandler = DataHandlerProvider.provide();
        this.mview.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void handleNotification() {

    }
}
