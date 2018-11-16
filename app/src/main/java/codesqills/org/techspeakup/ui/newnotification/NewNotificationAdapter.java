package codesqills.org.techspeakup.ui.newnotification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.Message;


/**
 * Created by kamalshree on 11/9/2018.
 */

public class NewNotificationAdapter extends RecyclerView.Adapter<NewNotificationAdapter.NotificationViewHolder> {

    private List<Message> mNotificationsList;
    private Context context;
    private NewNotificationAdapter.NotificationsItemListener mNotificationsItemListener;

    /**
     * Parameterized constructor. Takes Followers iteraction listener as parameter
     *
     * @param notificationsItemListener a {@link NewNotificationAdapter.NotificationsItemListener} for listening to click Messages on Messages items
     */
    NewNotificationAdapter(@NonNull NewNotificationAdapter.NotificationsItemListener notificationsItemListener, Context context) {
        mNotificationsList = new ArrayList<>();
        this.context = context;
        this.mNotificationsItemListener = notificationsItemListener;
    }

    @NonNull
    @Override
    public NewNotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notification, parent, false);
        return new NewNotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewNotificationAdapter.NotificationViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNotificationsList.size();
    }

    /**
     * fo
     * Clears current Messages list and displays the new list
     *
     * @param messagesList a {@link List} of {@link String}es to be displayed.
     */
    void loadFollowers(@NonNull List<Message> messagesList) {
        this.mNotificationsList.clear();
        this.mNotificationsList.addAll(messagesList);
        notifyDataSetChanged();
    }

    /**
     * Add new Messages to current list
     *
     * @param messagesList a {@link List} of {@link String}es to be added to current list
     */
    @SuppressWarnings("unused")
    void addMessages(@NonNull List<Message> messagesList) {
        // Removing the Messages before adding, this ensure no duplication of Messages
        this.mNotificationsList.removeAll(messagesList);
        this.mNotificationsList.addAll(messagesList);
        notifyDataSetChanged();
    }

    /**
     * {@link RecyclerView.ViewHolder} object for Messages item.
     */
    class NotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFollowerMessage;
        private TextView tvFollowerTimestamp;
        private ImageView deleteNotificationIcon;

        NotificationViewHolder(View itemView) {
            super(itemView);
            tvFollowerMessage = itemView.findViewById(R.id.tv_notifiation_message);
            tvFollowerTimestamp = itemView.findViewById(R.id.tv_notifiation_timestamp);
            deleteNotificationIcon = itemView.findViewById(R.id.iv_delete_notification);

        }

        void bind(int position) {

            final Context context = itemView.getContext();

            final Message currentNotifications = mNotificationsList.get(position);
            // Binding the data
            tvFollowerMessage.setText(currentNotifications.getMessage());
            tvFollowerTimestamp.setText(currentNotifications.getTimestamp());
            // Attaching click listener to each quiz item
            deleteNotificationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mNotificationsItemListener.onNotificationClicked(currentNotifications);
                }
            });

        }
    }

    /**
     * Callback interface for listening to click Messages on Messages items
     */
    interface NotificationsItemListener {
        /**
         * Called when Messages is clicked
         *
         * @param messages the Messages that was clicked
         */
        void onNotificationClicked(Message messages);
    }

}