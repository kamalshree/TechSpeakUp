package codesqills.org.techspeakup.data;

import java.util.List;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.Followers;

/**
 * Created by kamalshree on 10/25/2018.
 */

public interface DataHandler {

    void setUserInfo(Callback<Void> callback);

    void saveUserEmail(String userEmail);
    String getUserEmail();

    void saveUserName(String userName);
    String getUserName();

    void saveUserPic(String picUrl);
    String getUserPic();

    void saveUserType(String userType);
    String getUserType();

   //Edit Profile

    void saveEditName(String editName);
    String getEditName();

    void saveEditLocation(String editLocation);
    String getEditLocation();

    void saveEditJob(String editJob);
    String getEditJob();

    void saveEditTwitter(String editTwitter);
    String getEditTwitter();

    void saveEditLinkedin(String editLinkedin);
    String getEditLinkedin();

    void saveEditWebsite(String editWebsite);
    String getEditWebsite();

    void saveEditAboutMe(String editAboutMe);
    String getEditAboutMe();

    //Event Details
    void fetchEvents(int limitToFirst, Callback<List<Events>> callback);
    void fetchEventById(String eventId, Callback<Events> callback);

    //Followers
    void fetchFollowers(Callback<List<Followers>> callback);
   // void fetchFollowersDetails(String myUid, Callback<List<User>> callback);
    boolean isLoggedIn();
    void destroy();

    interface Callback<T> {
        void onResponse(T result);
        void onError();
    }
}
