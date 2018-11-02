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

}