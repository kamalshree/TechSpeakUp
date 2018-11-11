package codesqills.org.techspeakup.ui.followers;

import java.util.List;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/9/2018.
 */

public interface FollowersContract {

    interface View extends BaseView<Presenter>{
        void loadFollowers(List<String> followers);
    }

    interface Presenter extends BasePresenter{

    }
}
