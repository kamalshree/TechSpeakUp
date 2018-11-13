package codesqills.org.techspeakup.ui.newnotification;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NewNotificationPresenter implements NewNotificationContract.Presenter {

    NewNotificationContract.View mView;

    public NewNotificationPresenter(NewNotificationContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {
        this.mView = null;
    }

    @Override
    public void openNewNotification() {
        mView.navigateToNotificationFollowers();
    }
}
