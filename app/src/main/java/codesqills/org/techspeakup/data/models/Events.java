package codesqills.org.techspeakup.data.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kamalshree on 11/6/2018.
 */
@IgnoreExtraProperties
public class Events {

    @Expose
    @SerializedName("eventdate")
    private String mEventDate;

    @Expose
    @SerializedName("eventname")
    private String mEventName;

    @Expose
    @SerializedName("eventlocation")
    private String mEventLocation;

    @Expose
    @SerializedName("eventdetails")
    private String mEventDetails;

    @Exclude
    private String mKey;

    @PropertyName("eventdate")
    public String getEventDate() {
        return mEventDate;
    }

    @PropertyName("eventdate")
    public void setEventDate(String eventdate) {
        mEventDate = eventdate;
    }


    @PropertyName("eventname")
    public String getEventName() {
        return mEventName;
    }

    @PropertyName("eventname")
    public void setEventName(String eventname) {
        mEventName = eventname;
    }

    @PropertyName("eventlocation")
    public String getEventLocation() {
        return mEventLocation;
    }

    @PropertyName("eventlocation")
    public void setEventLocation(String eventlocation) {
        mEventLocation = eventlocation;
    }

    @PropertyName("eventdetails")
    public String getEventDetails() {
        return mEventDetails;
    }

    @PropertyName("eventdetails")
    public void setEventDetails(String eventdetails) {
        mEventDetails = eventdetails;
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
