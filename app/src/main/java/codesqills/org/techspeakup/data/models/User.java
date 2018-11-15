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
    @SerializedName("followers_count")
    private String mFollowersCount;

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

    @PropertyName("followers_count")
    public String getFollowersCount() {
        return mFollowersCount;
    }

    @PropertyName("followers_count")
    public void setFollowersCount(String followers_count) {
        mFollowersCount = followers_count;
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