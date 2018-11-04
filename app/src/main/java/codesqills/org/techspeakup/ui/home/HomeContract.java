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
    }
    interface Presenter extends BasePresenter{
        void getProfileDetails();
    }
}
