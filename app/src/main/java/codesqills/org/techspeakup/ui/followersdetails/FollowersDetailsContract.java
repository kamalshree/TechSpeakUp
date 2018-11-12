package codesqills.org.techspeakup.ui.followersdetails;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/12/2018.
 */

public interface FollowersDetailsContract {
    String KEY_FOLLOWERS_ID = "followers_id";

    interface View extends BaseView<Presenter>{
        void loadFollowertPic(String followerPic);
        void loadFollowertName(String followerName);
        void loadFollowertJob(String followerJob);
        void loadFollowertLocation(String followerLocation);
        void loadFollowertTwitter(String followerTwitter);
        void loadFollowertLinkedin(String followerLinkedin);
        void loadFollowertLink(String followerLink);
        void loadFollowertAbout(String followerAbout);
    }

    interface Presenter extends BasePresenter{


    }
}
