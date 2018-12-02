package codesqills.org.techspeakup.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kamalshree on 10/31/2018.
 */
 public class PrefsHelper {

    private static final String PREFERENCES_NAME = "techspeakup_app_prefs";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences.Editor editor;
    private SharedPreferences mPrefs;

    private static PrefsHelper sInstance = null;

    public PrefsHelper(Context context) {
        mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = mPrefs.edit();
    }

    public static PrefsHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PrefsHelper.class) {
                if (sInstance == null) {
                    sInstance = new PrefsHelper(context);
                }
            }
        }
        return sInstance;
    }

    // Check for first time installation of the app
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return mPrefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    private static final String KEY_USER_NAME = "key_user_name";
    private String mUserName;

    private static final String KEY_USER_EMAIL = "key_user_email";
    private String mUserEmail;

    private static final String KEY_USER_PIC = "key_user_image_url";
    private String mImageUrl;

    private static final String KEY_USER_STATUS = "key_user_status";
    private String mUserStatus;

    private static final String KEY_USER_TYPE = "key_user_type";
    private String mUserType;

    //Edit profile
    private static final String KEY_USER_LOCATION = "key_user_location";
    private String mUserLocation;
    private static final String KEY_USER_JOB = "key_user_job";
    private String mUserJob;
    private static final String KEY_USER_TWITTER = "key_user_twitter";
    private String mUserTwitter;
    private static final String KEY_USER_LINKEDIN= "key_user_linkedin";
    private String mUserLinkedin;
    private static final String KEY_USER_WEBSITE= "key_user_website";
    private String mUserWebsite;
    private static final String KEY_USER_ABOUT = "key_user_about";
    private String mUserAbout;
    private static final String KEY_USER_EVENTCOUNT = "key_user_eventcount";
    private String mUserEventCount;
    private static final String KEY_USER_EVENTDETAILS = "key_user_eventdetails";
    private String mUserEventDetails;
    private static final String KEY_USER_FOLLOWER_COUNT = "key_user_followercount";
    private String mUserFollowerCount;
    private static final String KEY_USER_RATE_COUNT = "key_user_ratecount";
    private String mUserRateCount;




    public void setUserName(String userName) {
        this.mUserName = userName;
        mPrefs.edit().putString(KEY_USER_NAME, userName).apply();
    }

    public String getUserName() {
        if (mUserName == null) {
            mUserName = mPrefs.getString(KEY_USER_NAME, null);
        }
        return mUserName;
    }

    public void setUserEmail(String email) {
        this.mUserEmail = email;
        mPrefs.edit().putString(KEY_USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        if (mUserEmail == null) {
            mUserEmail = mPrefs.getString(KEY_USER_EMAIL, null);
        }
        return mUserEmail;
    }

    public void setUserPic(String picUrl) {
        this.mImageUrl = picUrl;
        mPrefs.edit().putString(KEY_USER_PIC, picUrl).apply();
    }

    public String getUserPic() {
        if (mImageUrl == null) {
            mImageUrl = mPrefs.getString(KEY_USER_PIC, null);
        }
        return mImageUrl;
    }


    public void setUserStatus(String userStatus) {
        this.mUserStatus = userStatus;
        mPrefs.edit().putString(KEY_USER_STATUS, userStatus).apply();
    }

    public String getUserStatus() {
        if (mUserStatus == null) {
            mUserStatus = mPrefs.getString(KEY_USER_STATUS, null);
        }
        return mUserStatus;
    }

    public void setUserType(String userType) {
        this.mUserType = userType;
        mPrefs.edit().putString(KEY_USER_TYPE, userType).apply();
    }

    public String getUserType() {
        if (mUserType == null) {
            mUserType = mPrefs.getString(KEY_USER_TYPE, null);
        }
        return mUserType;
    }

    //Edit profile

    public void setUserLocation(String userLocation) {
        this.mUserLocation = userLocation;
        mPrefs.edit().putString(KEY_USER_LOCATION, userLocation).apply();
    }

    public String getUserLocation() {
        if (mUserLocation == null) {
                mUserLocation = mPrefs.getString(KEY_USER_LOCATION, null);
        }
        return mUserLocation;
    }

    public void setUserJob(String userJob) {
        this.mUserJob = userJob;
        mPrefs.edit().putString(KEY_USER_JOB, userJob).apply();
    }

    public String getUserJob() {
        if (mUserJob == null) {
            mUserJob = mPrefs.getString(KEY_USER_JOB, null);
        }
        return mUserJob;
    }

    public void setUserTwitter(String userTwitter) {
        this.mUserTwitter = userTwitter;
        mPrefs.edit().putString(KEY_USER_TWITTER, userTwitter).apply();
    }

    public String getUserTwitter() {
        if (mUserTwitter == null) {
            mUserTwitter = mPrefs.getString(KEY_USER_TWITTER, null);
        }
        return mUserTwitter;
    }

    public void setUserLinkedin(String userLinkedin) {
        this.mUserLinkedin = userLinkedin;
        mPrefs.edit().putString(KEY_USER_LINKEDIN, userLinkedin).apply();
    }

    public String getUserLinkedin() {
        if (mUserLinkedin == null) {
            mUserLinkedin = mPrefs.getString(KEY_USER_LINKEDIN, null);
        }
        return mUserLinkedin;
    }

    public void setUserWebsite(String userWebsite) {
        this.mUserWebsite = userWebsite;
        mPrefs.edit().putString(KEY_USER_WEBSITE, userWebsite).apply();
    }

    public String getUserWebsite() {
        if (mUserWebsite == null) {
            mUserWebsite = mPrefs.getString(KEY_USER_WEBSITE, null);
        }
        return mUserWebsite;
    }

    public void setUserAbout(String userAbout) {
        this.mUserAbout = userAbout;
        mPrefs.edit().putString(KEY_USER_ABOUT, userAbout).apply();
    }

    public String getUserAbout() {
        if (mUserAbout == null) {
            mUserAbout = mPrefs.getString(KEY_USER_ABOUT, null);
        }
        return mUserAbout;
    }

    public void setUserEventCount(String userEventCount) {
        this.mUserEventCount = userEventCount;
        mPrefs.edit().putString(KEY_USER_EVENTCOUNT, userEventCount).apply();
    }

    public String getUserEventCount() {
        if (mUserEventCount == null) {
            mUserEventCount = mPrefs.getString(KEY_USER_EVENTCOUNT, null);
        }
        return mUserEventCount;
    }

    public void setUserEventDetails(String userEventDetails) {
        this.mUserEventDetails = userEventDetails;
        mPrefs.edit().putString(KEY_USER_EVENTDETAILS, userEventDetails).apply();
    }

    public String getUserEventDetails() {
        if (mUserEventDetails == null) {
            mUserEventDetails = mPrefs.getString(KEY_USER_EVENTDETAILS, null);
        }
        return mUserEventDetails;
    }


    public void setUserFollowerCount(String userFollowerCount) {
        this.mUserFollowerCount = userFollowerCount;
        mPrefs.edit().putString(KEY_USER_FOLLOWER_COUNT, userFollowerCount).apply();
    }

    public String getUserFollowerCount() {
        if (mUserFollowerCount == null) {
            mUserFollowerCount = mPrefs.getString(KEY_USER_FOLLOWER_COUNT, null);
        }
        return mUserFollowerCount;
    }

    public void setUserRateCount(String userRateCount) {
        this.mUserRateCount = userRateCount;
        mPrefs.edit().putString(KEY_USER_RATE_COUNT, userRateCount).apply();
    }

    public String getUserRateCount() {
        if (mUserRateCount == null) {
            mUserRateCount = mPrefs.getString(KEY_USER_RATE_COUNT, null);
        }
        return mUserRateCount;
    }




    public void destroy() {
        mPrefs.edit().clear().apply();
        mUserStatus = null;
        mImageUrl = null;
        mUserEmail = null;
        mUserType = null;
        mUserName = null;
        mUserJob=null;
        mUserTwitter=null;
        mUserLinkedin=null;
        mUserWebsite=null;
        mUserAbout=null;
        mUserFollowerCount=null;
        mUserRateCount=null;

    }

}