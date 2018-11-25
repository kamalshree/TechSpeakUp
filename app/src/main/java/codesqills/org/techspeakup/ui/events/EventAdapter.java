package codesqills.org.techspeakup.ui.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Events;

/**
 * Created by kamalshree on 11/6/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventsViewHolder> {

    private List<Events> mEventsList;
    private EventsItemListener mEventsItemListener;

    /**
     * Parameterized constructor. Takes Events iteraction listener as parameter
     *
     * @param eventsItemListener a {@link EventsItemListener} for listening to click events on Events items
     */
    EventAdapter(@NonNull EventsItemListener eventsItemListener) {
        mEventsList = new ArrayList<>();
        this.mEventsItemListener = eventsItemListener;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_events, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    /**
     * Clears current Events list and displays the new list
     *
     * @param eventsList a {@link List} of {@link Events}es to be displayed.
     */
    void loadEvents(@NonNull List<Events> eventsList) {
        this.mEventsList.clear();
        this.mEventsList.addAll(eventsList);

        notifyDataSetChanged();
    }

    /**
     * Add new events to current list
     *
     * @param eventsList a {@link List} of {@link Events}es to be added to current list
     */
    @SuppressWarnings("unused")
    void addEvents(@NonNull List<Events> eventsList) {
        // Removing the events before adding, this ensure no duplication of events
        this.mEventsList.removeAll(eventsList);
        this.mEventsList.addAll(eventsList);
        notifyDataSetChanged();
    }

    /**
     * {@link android.support.v7.widget.RecyclerView.ViewHolder} object for events item.
     */
    class EventsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_event_name)
        TextView tvEventName;
        @BindView(R.id.tv_event_date)
        TextView tvEventDate;
        @BindView(R.id.tv_event_location)
        TextView tvEventLocation;


        EventsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {

            final Context context = itemView.getContext();

            final Events currentEvents = mEventsList.get(position);

            // Binding the data
            tvEventName.setText(currentEvents.getEventName());
            tvEventDate.setText(currentEvents.getEventDate());
            tvEventLocation.setText(currentEvents.getEventLocation());
            // Attaching click listener to each quiz item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEventsItemListener.onEventsClicked(currentEvents);
                }
            });
        }
    }

    /**
     * Callback interface for listening to click events on events items
     */
    interface EventsItemListener {
        /**
         * Called when events is clicked
         *
         * @param events the events that was clicked
         */
        void onEventsClicked(Events events);
    }

}