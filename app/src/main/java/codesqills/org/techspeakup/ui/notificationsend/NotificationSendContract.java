package codesqills.org.techspeakup.ui.notificationsend;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/12/2018.
 */

public interface NotificationSendContract {
    String KEY_FOLLOWER_SEND_ID = "user_id";
    String KEY_FOLLOWER_SEND_NAME = "user_name";

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void handleNotification();
    }
}
