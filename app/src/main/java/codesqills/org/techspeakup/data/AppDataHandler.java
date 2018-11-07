package codesqills.org.techspeakup.data;


import android.content.Context;

import codesqills.org.techspeakup.application.AppClass;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.data.remote.FirebaseHandler;
import codesqills.org.techspeakup.data.remote.FirebaseProvider;

/**
 * Created by kamalshree on 10/25/2018.
 */

class AppDataHandler implements DataHandler {
    private static AppDataHandler INSTANCE = null;
    private FirebaseHandler mFirebaseHandler;
    private PrefsHelper mPreferences;


    private AppDataHandler() {
        Context context = AppClass.getAppContext();
        mPreferences = PrefsHelper.getInstance(context);
            mFirebaseHandler = FirebaseProvider.provide();
    }

    static AppDataHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (AppDataHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDataHandler();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void setUserInfo(Callback<Void> callback) {
        User currentUser = new User();
        currentUser.setImage(mPreferences.getUserPic());
        currentUser.setName(mPreferences.getUserName());
        currentUser.setEmail(mPreferences.getUserEmail());
        currentUser.setType(mPreferences.getUserType());
        currentUser.setLocation(mPreferences.getUserLocation());
        currentUser.setJob(mPreferences.getUserJob());
        currentUser.setTwitter(mPreferences.getUserTwitter());
        currentUser.setLinkedin(mPreferences.getUserLinkedin());
        currentUser.setWebsite(mPreferences.getUserWebsite());
        currentUser.setAbout(mPreferences.getUserAbout());

        mFirebaseHandler.setUserInfo(currentUser, new FirebaseCallback<>(callback));
    }

    @Override
    public void saveUserEmail(String userEmail) {
        mPreferences.setUserEmail(userEmail);
    }


    @Override
    public String getUserEmail() {
        return mPreferences.getUserEmail();
    }

    @Override
    public void saveUserName(String userName) {
        mPreferences.setUserName(userName);
    }

    @Override
    public String getUserName() {
        return mPreferences.getUserName();
    }

    @Override
    public void saveUserPic(String picUrl) {
        mPreferences.setUserPic(picUrl);
    }

    @Override
    public String getUserPic() {
        return mPreferences.getUserPic();
    }

    @Override
    public void saveUserType(String userType) {
        mPreferences.setUserType(userType);
    }

    @Override
    public String getUserType() {
        return mPreferences.getUserType();
    }

    @Override
    public boolean isLoggedIn() {
        return (mPreferences.getUserType() != null);
    }

    // Edit Profile setters and getters
    @Override
    public void saveEditName(String userName) {
        mPreferences.setUserName(userName);
    }

    @Override
    public String getEditName() {
        return mPreferences.getUserName();
    }

    @Override
    public void saveEditLocation(String userLocation) {
        mPreferences.setUserLocation(userLocation);
    }

    @Override
    public String getEditLocation() {
        return mPreferences.getUserLocation();
    }

    @Override
    public void saveEditJob(String userJob) {
        mPreferences.setUserJob(userJob);
    }

    @Override
    public String getEditJob() {
        return mPreferences.getUserJob();
    }

    @Override
    public void saveEditTwitter(String userTwitter) {
        mPreferences.setUserTwitter(userTwitter);
    }

    @Override
    public String getEditTwitter() {
        return mPreferences.getUserTwitter();
    }

    @Override
    public void saveEditLinkedin(String userLinkedin) {mPreferences.setUserLinkedin(userLinkedin);
    }

    @Override
    public String getEditLinkedin() {
        return mPreferences.getUserLinkedin();
    }

    @Override
    public void saveEditWebsite(String userWebsite) {mPreferences.setUserWebsite(userWebsite);
    }

    @Override
    public String getEditWebsite() {
        return mPreferences.getUserWebsite();
    }

    @Override
    public void saveEditAboutMe(String userAbout) {mPreferences.setUserAbout(userAbout);
    }

    @Override
    public String getEditAboutMe() {
        return mPreferences.getUserAbout();
    }

    @Override
    public void destroy() {
        mPreferences.destroy();
        mFirebaseHandler.destroy();
    }

    class FirebaseCallback<T> implements FirebaseHandler.Callback<T> {
        DataHandler.Callback<T> callback;

        FirebaseCallback(DataHandler.Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onReponse(T result) {
            this.callback.onResponse(result);
        }

        @Override
        public void onError() {
            this.callback.onError();
        }
    }
}
