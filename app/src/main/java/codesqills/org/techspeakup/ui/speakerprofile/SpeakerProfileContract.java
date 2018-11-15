package codesqills.org.techspeakup.ui.speakerprofile;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/4/2018.
 */

public interface SpeakerProfileContract {

    interface View extends BaseView<Presenter>{
        void loadProfileImage(String picUrl);
        void loadUserName(String username);
        void loadUserLocation(String location);
        void loadUserJob(String job);
        void loadUserTwitter(String twiiter);
        void loadUserLinkedin(String linkedin);
        void loadUserWebsite(String website);
        void loadUserAbout(String about);
    }

    interface Presenter extends BasePresenter{
        void getProfileImage();
        void getUserName();
        void getUserLocation();
        void getUserJob();
        void getUserTwitter();
        void getUserLinkedin();
        void getUserWebsite();
        void getUserAbout();
    }
}
