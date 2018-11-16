package codesqills.org.techspeakup.ui.eventsdetails;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/7/2018.
 */

public interface EventsDetailsContract {

    String KEY_EVENTS_ID = "events_id";
    String KEY_EVENTS_NAME = "events_name";
    String KEY_EVENTS_LOCATION = "events_location";
    String KEY_EVENTS_DATE = "events_date";


    interface View extends BaseView<Presenter> {
        void loadEventName(String eventName);

        void loadEventDate(String eventDate);

        void loadEventLocation(String eventLocation);

        void loadEventDetails(String eventDetails);
    }

    interface Presenter extends BasePresenter {

    }
}
