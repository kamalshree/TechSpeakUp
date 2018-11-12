package codesqills.org.techspeakup.data.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kamalshree on 11/10/2018.
 */

public class Followers {
    @Expose
    @SerializedName("followersname")
    private String mFollowersName;

    @Expose
    @SerializedName("followersjob")
    private String mFollowersJob;

    @Expose
    @SerializedName("followersemail")
    private String mFollowersEmail;

    @Expose
    @SerializedName("followerspic")
    private String mFollowersPic;

    @Exclude
    private String mKey;

    @PropertyName("followersname")
    public String getmFollowersName() {
        return mFollowersName;
    }

    @PropertyName("followersname")
    public void setmFollowersName(String mFollowersName) {
        this.mFollowersName = mFollowersName;
    }

    @PropertyName("followersjob")
    public String getmFollowersJob() {
        return mFollowersJob;
    }

    @PropertyName("followersjob")
    public void setmFollowersJob(String mFollowersJob) {
        this.mFollowersJob = mFollowersJob;
    }

    @PropertyName("followersemail")
    public String getmFollowersEmail() {
        return mFollowersEmail;
    }

    @PropertyName("followersemail")
    public void setmFollowersEmail(String mFollowersEmail) {
        this.mFollowersEmail = mFollowersEmail;
    }

    @PropertyName("followerspic")
    public String getmFollowersPic() {
        return mFollowersPic;
    }

    @PropertyName("followerspic")
    public void setmFollowersPic(String mFollowersPic) {
        this.mFollowersPic = mFollowersPic;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
