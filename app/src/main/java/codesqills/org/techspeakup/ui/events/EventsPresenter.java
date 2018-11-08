package codesqills.org.techspeakup.ui.events;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.data.DataHandler;
import codesqills.org.techspeakup.data.DataHandlerProvider;
import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.ui.home.HomeContract;

/**
 * Created by kamalshree on 11/6/2018.
 */

public class EventsPresenter implements EventsContract.Presenter {
    private DataHandler mDataHandler;
    private EventsContract.View mview;
    private List<Events> mEvents;

    public EventsPresenter(EventsContract.View view) {
        this.mview = view;
        this.mDataHandler = DataHandlerProvider.provide();

        mEvents = new ArrayList<>();
        this.mview.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {
        mview.showLoading();
        mDataHandler.fetchEvents(0, new DataHandler.Callback<List<Events>>() {
            @Override
            public void onResponse(List<Events> result) {
                mEvents.clear();
                mEvents.addAll(result);
                mview.loadEvents(result);
                mview.hideLoading();

            }

            @Override
            public void onError() {
                mview.loadEventsError();
                mview.hideLoading();
            }
        });
    }

    @Override
    public void destroy() {
        this.mview = null;
        this.mDataHandler = null;
    }

    @Override
    public void onEventsClicked(Events events) {
        mview.navigateToEventsDesc(events);
    }
}
