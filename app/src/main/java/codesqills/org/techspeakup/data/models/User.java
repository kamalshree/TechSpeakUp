package codesqills.org.techspeakup.data.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by kamalshree on 10/29/2018.
 */

@IgnoreExtraProperties
public class User {

    @Expose
    @SerializedName("email")
    private String mEmail;

    @Expose
    @SerializedName("image")
    private String mImage;

    @Expose
    @SerializedName("name")
    private String mName;


    @Expose
    @SerializedName("type")
    private String mType;

    @Expose
    @SerializedName("location")
    private String mLocation;

    @Expose
    @SerializedName("job")
    private String mJob;

    @Expose
    @SerializedName("twitter")
    private String mTwitter;

    @Expose
    @SerializedName("linkedin")
    private String mLinkedin;

    @Expose
    @SerializedName("website")
    private String mWebsite;

    @Expose
    @SerializedName("about")
    private String mAbout;

    @Expose
    @SerializedName("event_count")
    private String mEventCount;

    @Expose
    @SerializedName("event_details")
    private String mEvent_details;

    @Expose
    @SerializedName("followers_count")
    private String mFollowersCount;

    @Expose
    @SerializedName("rate_count")
    private String mRateCount;

    @Expose
    @SerializedName("user_latitude")
    private double mLatitude;

    @Expose
    @SerializedName("user_longitude")
    private double mLongitude;

    /**
     * This field should be used for storing key of realtime database snapshot, otherwise ignore it
     */
    @Exclude
    private String mKey;

    @PropertyName("email")
    public String getEmail() {
        return mEmail;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        mEmail = email;
    }

    @PropertyName("image")
    public String getImage() {
        return mImage;
    }

    @PropertyName("image")
    public void setImage(String image) {
        mImage = image;
    }


    @PropertyName("name")
    public String getName() {
        return mName;
    }

    @PropertyName("name")
    public void setName(String name) {
        mName = name;
    }


    @PropertyName("type")
    public String getType() {
        return mType;
    }

    @PropertyName("type")
    public void setType(String type) {
        mType = type;
    }

    //Edit Fields
    @PropertyName("location")
    public String getLocation() {
        return mLocation;
    }

    @PropertyName("location")
    public void setLocation(String location) {
        mLocation = location;
    }


    @PropertyName("job")
    public String getJob() {
        return mJob;
    }

    @PropertyName("job")
    public void setJob(String job) {
        mJob = job;
    }


    @PropertyName("twitter")
    public String getTwitter() {
        return mTwitter;
    }

    @PropertyName("twitter")
    public void setTwitter(String twitter) {
        mTwitter = twitter;
    }

    @PropertyName("linkedin")
    public String getLinkedin() {
        return mLinkedin;
    }

    @PropertyName("linkedin")
    public void setLinkedin(String linkedin) {
        mLinkedin = linkedin;
    }

    @PropertyName("website")
    public String getWebsite() {
        return mWebsite;
    }

    @PropertyName("website")
    public void setWebsite(String website) {
        mWebsite = website;
    }

    @PropertyName("about")
    public String getAbout() {
        return mAbout;
    }

    @PropertyName("about")
    public void setAbout(String about) {
        mAbout = about;
    }

    @PropertyName("event_count")
    public String getEventCount() {
        return mEventCount;
    }

    @PropertyName("event_count")
    public void setEventCount(String event_count) {
        mEventCount = event_count;
    }

    @PropertyName("event_details")
    public String getEventDetails() {
        return mEvent_details;
    }

    @PropertyName("event_details")
    public void setEventDetails(String event_details) {
        mEvent_details = event_details;
    }

    @PropertyName("followers_count")
    public String getFollowersCount() {
        return mFollowersCount;
    }

    @PropertyName("followers_count")
    public void setFollowersCount(String followers_count) {
        mFollowersCount = followers_count;
    }

    @PropertyName("rate_count")
    public String getRateCount() {
        return mRateCount;
    }

    @PropertyName("rate_count")
    public void setRateCount(String rate_count) {
        mRateCount = rate_count;
    }

    @PropertyName("user_latitude")
    public double getLatitude() {
        return mLatitude;
    }

    @PropertyName("user_latitude")
    public void setLatitude(double user_latitude) {
        mLatitude = user_latitude;
    }

    @PropertyName("user_longitude")
    public double getLongitude() {
        return mLongitude;
    }

    @PropertyName("user_longitude")
    public void setLongitude(double user_longitude) {
        mLongitude = user_longitude;
    }


    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        this.mKey = key;
    }
}