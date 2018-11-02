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

    boolean isLoggedIn();
    void destroy();

    interface Callback<T> {
        void onResponse(T result);
        void onError();
    }
}
