package codesqills.org.techspeakup.data.remote;

import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 10/31/2018.
 */

public interface FirebaseHandler {

    void updateUserName(String userName, Callback<Void> callback);

    void updateProfilePic(String profielPicUrl, Callback<Void> callback);

    //edit profile

    void updateEditLocation(String userLocation, Callback<Void> callback);

    void updateEditJob(String userJob, Callback<Void> callback);

    void updateEditTwitter(String userTwitter, Callback<Void> callback);

    void updateEditLinkedin(String userLinkedin, Callback<Void> callback);

    void updateEditWebsite(String userWebsite, Callback<Void> callback);

    void updateUserAbout(String userAbout, Callback<Void> callback);


    void setUserInfo(User currentUser, Callback<Void> callback);
    void destroy();

    String REF_USERS_NODE = "users";

    interface Callback<T> {
        void onReponse(T result);

        void onError();
    }
}
