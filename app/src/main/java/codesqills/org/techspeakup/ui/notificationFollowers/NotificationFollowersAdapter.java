package codesqills.org.techspeakup.ui.notificationFollowers;

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
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 11/12/2018.
 */

public class NotificationFollowersAdapter extends RecyclerView.Adapter<NotificationFollowersAdapter.NotificiationViewHolder> {

    private List<User> mNotificationsList;
    private NotificationItemListener mNotificationItemListener;

    /**
     * Parameterized constructor. Takes Events iteraction listener as parameter
     *
     * @param notificationItemListener a {@link NotificationItemListener} for listening to click events on Events items
     */
    NotificationFollowersAdapter(@NonNull NotificationItemListener notificationItemListener) {
        mNotificationsList = new ArrayList<>();
        this.mNotificationItemListener = notificationItemListener;
    }


    @NonNull
    @Override
    public NotificationFollowersAdapter.NotificiationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notification_followers, parent, false);
        return new NotificationFollowersAdapter.NotificiationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationFollowersAdapter.NotificiationViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNotificationsList.size();
    }

    /**
     * Clears current followeres list and displays the new list
     *
     * @param followersList a {@link List} of {@link User}es to be displayed.
     */
    void loadFollowers(@NonNull List<User> followersList) {
        this.mNotificationsList.clear();
        this.mNotificationsList.addAll(followersList);
        notifyDataSetChanged();
    }


    /**
     * {@link android.support.v7.widget.RecyclerView.ViewHolder} object for events item.
     */
    class NotificiationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_notification_followers_name)
        TextView tvFollowerName;
        @BindView(R.id.tv_notification_followers_designation)
        TextView tvFollowerDesignation;
        @BindView(R.id.tv_notification_followers_location)
        TextView tvFollowerLocation;

        NotificiationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {

            final Context context = itemView.getContext();

            final User currentEvents = mNotificationsList.get(position);

            // Binding the data
            tvFollowerName.setText(currentEvents.getName());
            tvFollowerDesignation.setText(currentEvents.getJob());
            tvFollowerLocation.setText(currentEvents.getLocation());
            // Attaching click listener to each quiz item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNotificationItemListener.onFollowersClicked(currentEvents);
                }
            });
        }
    }

    /**
     * Callback interface for listening to click events on events items
     */
    interface NotificationItemListener {
        /**
         * Called when events is clicked
         *
         * @param followers the events that was clicked
         */
        void onFollowersClicked(User followers);
    }

}