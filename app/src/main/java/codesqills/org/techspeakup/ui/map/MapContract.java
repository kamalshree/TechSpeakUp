package codesqills.org.techspeakup.ui.map;

import java.util.List;

import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/21/2018.
 */

public interface MapContract {

    interface View extends BaseView<Presenter> {
        void loadUserDetails(List<User> events);

    }

    interface Presenter extends BasePresenter {
    }
}
