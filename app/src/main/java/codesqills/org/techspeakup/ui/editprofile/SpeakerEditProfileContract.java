package codesqills.org.techspeakup.ui.editprofile;

import android.support.annotation.Nullable;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/5/2018.
 */

public interface SpeakerEditProfileContract {

    interface View extends BaseView<Presenter>{
        void setEditName(String name);
        void setEditLocation(String location);
        void setEditJob(String job);
        void setEditTwitter(String twiiter);
        void setEditLinkedin(String linkedin);
        void setEditWebsite(String website);
        void setEditAbout(String about);
        void setEditEventCount(String eventCount);
        void setEditEventDetails(String eventDetails);
        void onProfileSaved();
        void onSaveError();
    }
    interface Presenter extends BasePresenter{
        void getEditName();
        void getEditLocation();
        void getEditJob();
        void getEditTwitter();
        void getEditLinkedin();
        void getEditWebsite();
        void getEditAbout(); void getEditEventCount(); void getEditEventDetails();
        void saveEditProfile(@Nullable String editName,String editLocation,String editJob,String editTwitter,String editLinkedin,String editWebsite,String editAboutMe,String followerCount,String rateCount,String editEventCount,String editEventDetails);
    }
}
