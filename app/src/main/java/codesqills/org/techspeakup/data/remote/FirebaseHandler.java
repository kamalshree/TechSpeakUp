package codesqills.org.techspeakup.data.remote;

import java.util.List;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.Message;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 10/31/2018.
 */

public interface FirebaseHandler {


    void fetchEvents(int limitToFirst, Callback<List<Events>> callback);
    void fetchEventById(String eventId, Callback<Events> callback);

    void fetchFollowers(Callback<List<Followers>> callback);
    void fetchSpeakers(Callback<List<User>> callback);
    void fetchFollowersById(String followerId, Callback<User> callback);

    void fetchAllUsers(int limitToFirst, Callback<List<User>> callback);
    //User Map
    void fetchAllUsersMap(int limitToFirst, Callback<List<User>> callback);

    void fetchNotifications(Callback<List<Message>> callback);


    void setUserInfo(User currentUser, Callback<Void> callback);
    void destroy();

    String REF_USERS_NODE = "users";
    String REF_EVENTS_NODE = "events";
    String REF_FOLLOWERS_NODE = "followers";
    String REF_NOTIFICATIONS_NODE = "notifications";

    interface Callback<T> {
        void onReponse(T result);

        void onError();
    }
}
