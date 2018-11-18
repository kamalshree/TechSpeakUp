package codesqills.org.techspeakup.ui.search;

import java.util.List;

import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;
import codesqills.org.techspeakup.ui.userdashboard.UserDashboardContract;

/**
 * Created by kamalshree on 11/17/2018.
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void loadUserDetails(List<User> events);

    }

    interface Presenter extends BasePresenter{
    }
}
