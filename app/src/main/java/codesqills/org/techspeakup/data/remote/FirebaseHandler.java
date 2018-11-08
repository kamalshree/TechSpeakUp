package codesqills.org.techspeakup.data.remote;

import java.util.List;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 10/31/2018.
 */

public interface FirebaseHandler {


    void fetchEvents(int limitToFirst, Callback<List<Events>> callback);
    void fetchEventById(String eventId, Callback<Events> callback);

    void setUserInfo(User currentUser, Callback<Void> callback);
    void destroy();

    String REF_USERS_NODE = "users";
    String REF_EVENTS_NODE = "events";

    interface Callback<T> {
        void onReponse(T result);

        void onError();
    }
}
