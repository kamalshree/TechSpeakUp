package codesqills.org.techspeakup.ui.notificationFollowers;

import java.util.List;

import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/12/2018.
 */

public interface NotificationFollowersContract {

    interface View extends BaseView<Presenter> {
        void loadFollowers(List<User> events);
        void navigateToNotificationDetails(User users);
        void loadFollowersError();
    }

    interface Presenter extends BasePresenter {
        void onFollowersClicked(User user);
    }
}
