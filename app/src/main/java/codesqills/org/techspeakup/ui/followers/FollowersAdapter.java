package codesqills.org.techspeakup.ui.followers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.User;


/**
 * Created by kamalshree on 11/9/2018.
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder> {

    private List<String> mFollowersList;
    private Context context;
    private FollowersAdapter.FollowersItemListener mFollowersItemListener;

    /**
     * Parameterized constructor. Takes Followers iteraction listener as parameter
     *
     * @param followersItemListener a {@link FollowersAdapter.FollowersItemListener} for listening to click events on Events items
     */
    FollowersAdapter(@NonNull FollowersAdapter.FollowersItemListener followersItemListener, Context context) {
        mFollowersList = new ArrayList<>();
        this.context=context;
        this.mFollowersItemListener = followersItemListener;
    }

    @NonNull
    @Override
    public FollowersAdapter.FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_followers, parent, false);
        return new FollowersAdapter.FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersAdapter.FollowersViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mFollowersList.size();
    }

    /**fo
     * Clears current Events list and displays the new list
     *
     * @param followersList a {@link List} of {@link String}es to be displayed.
     */
    void loadFollowers(@NonNull List<String> followersList) {
        this.mFollowersList.clear();
        this.mFollowersList.addAll(followersList);
        notifyDataSetChanged();
    }

    /**
     * Add new events to current list
     *
     * @param followersList a {@link List} of {@link String}es to be added to current list
     */
    @SuppressWarnings("unused")
    void addFollowers(@NonNull List<String> followersList) {
        // Removing the events before adding, this ensure no duplication of events
        this.mFollowersList.removeAll(followersList);
        this.mFollowersList.addAll(followersList);
        notifyDataSetChanged();
    }

    /**
     * {@link android.support.v7.widget.RecyclerView.ViewHolder} object for events item.
     */
    class FollowersViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFollowerName;
        private ImageView imFollowerPic;

        FollowersViewHolder(View itemView) {
            super(itemView);
            tvFollowerName = itemView.findViewById(R.id.tv_follower_name);
            imFollowerPic = itemView.findViewById(R.id.img_follower_pic);

        }

        void bind(int position) {

            final Context context = itemView.getContext();

            final String currentEvents = mFollowersList.get(position);
            // Binding the data
            tvFollowerName.setText(currentEvents);
            //Glide.with(context).load(currentEvents.getImage()).apply(RequestOptions.circleCropTransform()).into(imFollowerPic);
            // Attaching click listener to each quiz item
        }
    }

    /**
     * Callback interface for listening to click events on events items
     */
    interface FollowersItemListener {
        /**
         * Called when events is clicked
         *
         * @param events the events that was clicked
         */
        //  void onFollowersClicked(Events events);
    }

}