package codesqills.org.techspeakup.ui.events;

import java.util.List;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/6/2018.
 */

public interface EventsContract {

    interface View extends BaseView<Presenter>{
        void loadEvents(List<Events> events);
        void loadEventsError();
        void navigateToEventsDesc(Events events);
    }

    interface Presenter extends BasePresenter{
        void onEventsClicked(Events events);
    }
}
