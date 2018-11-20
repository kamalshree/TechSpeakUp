package codesqills.org.techspeakup.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kamalshree on 11/18/2018.
 */

public class Rate {

    private String rate;
    private String user_id;
    private String key;

    public Rate(String rate, String user_id, String timestamp) {
        this.rate = rate;
        this.user_id = user_id;
    }

    public Rate() {

    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + rate + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
