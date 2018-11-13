package codesqills.org.techspeakup.ui.home;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/3/2018.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter>{
        void statusProfileDetails();
        void loadMyUserName(String username);
        void displayUserProfile();
        void displayEditProfile();
        void displayFollowers();
        void displayEvent();
        void displayNotification();
    }
    interface Presenter extends BasePresenter{
        void getProfileDetails();
        void handleUserProfile();
        void handleUserFollowers();
        void handleEditProfile();
        void handleUserEvent();
        void handleNotification();
    }
}
