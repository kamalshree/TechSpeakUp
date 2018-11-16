package codesqills.org.techspeakup.ui.newnotification;

import java.util.List;

import codesqills.org.techspeakup.data.models.Message;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/12/2018.
 */

public interface NewNotificationContract {

    interface View extends BaseView<Presenter> {
        void navigateToNotificationFollowers();

        void loadNotifications(List<Message> messages);
        void  deleteNotifications(Message message);

    }

    interface Presenter extends BasePresenter {
        void openNewNotification();
        void onNotificationClicked(Message message);
    }
}
