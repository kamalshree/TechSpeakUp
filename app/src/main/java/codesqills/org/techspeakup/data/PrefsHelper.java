package codesqills.org.techspeakup.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kamalshree on 10/31/2018.
 */
 class PrefsHelper {

    private static final String PREFERENCES_NAME = "techspeakup_app_prefs";

    private SharedPreferences mPrefs;

    private static PrefsHelper sInstance = null;

    private PrefsHelper(Context context) {
        mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
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

    public void destroy() {
        mPrefs.edit().clear().apply();
        mUserStatus = null;
        mImageUrl = null;
        mUserEmail = null;
        mUserType = null;
        mUserName = null;
    }

}