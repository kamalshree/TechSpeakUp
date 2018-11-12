package codesqills.org.techspeakup.ui.followers;

import java.util.List;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/9/2018.
 */

public interface FollowersContract {

    interface View extends BaseView<Presenter> {
        void loadFollowers(List<Followers> followers);
        void navigateToFollowersProfile(Followers followers);

    }

    interface Presenter extends BasePresenter {
        void onFollowersClicked(Followers followers);
    }
}
