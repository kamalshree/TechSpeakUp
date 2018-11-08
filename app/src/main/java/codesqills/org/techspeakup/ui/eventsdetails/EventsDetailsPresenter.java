package codesqills.org.techspeakup.ui.eventsdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Events;

/**
 * Created by kamalshree on 11/7/2018.
 */

public class EventsDetailsPresenter implements EventsDetailsContract.Presenter{

    EventsDetailsContract.View mView;
    private DataHandler mDataHandler;
    private String mCurrentEventId;

    public EventsDetailsPresenter(EventsDetailsContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerProvider.provide();

        // This should be the last statement
        this.mView.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        String eventId = extras.getString(EventsDetailsContract.KEY_EVENTS_ID);
        this.mCurrentEventId = eventId;

        mDataHandler.fetchEventById(eventId, new DataHandler.Callback<Events>() {
            @Override
            public void onResponse(Events result) {

                mView.loadEventName(result.getEventName());
                mView.loadEventDate(result.getEventDate());
                mView.loadEventLocation(result.getEventLocation());
                mView.loadEventDetails(result.getEventDetails());

            }

            @Override
            public void onError() {
                mView.hideLoading();
            }
        });
    }

    @Override
    public void destroy() {
        this.mView=null;
        this.mDataHandler=null;
    }


}
