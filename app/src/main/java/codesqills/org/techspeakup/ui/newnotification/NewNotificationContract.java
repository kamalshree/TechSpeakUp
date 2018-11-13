package codesqills.org.techspeakup.ui.newnotification;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/12/2018.
 */

public interface NewNotificationContract {

    interface View extends BaseView<Presenter>{
        void navigateToNotificationFollowers();
    }

    interface Presenter extends BasePresenter{
        void openNewNotification();
    }
}
