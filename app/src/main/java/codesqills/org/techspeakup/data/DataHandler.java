package codesqills.org.techspeakup.data;

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

    boolean isLoggedIn();
    void destroy();

    interface Callback<T> {
        void onResponse(T result);
        void onError();
    }
}
