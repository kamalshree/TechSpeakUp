package codesqills.org.techspeakup.ui.speaker;

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
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.followers.FollowersAdapter;

/**
 * Created by kamalshree on 11/18/2018.
 */

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.SpeakersViewHolder> {

    private List<User> mSpeakersList;
    private Context context;

    SpeakerAdapter( Context context) {
        mSpeakersList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public SpeakerAdapter.SpeakersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_speakers, parent, false);
        return new SpeakerAdapter.SpeakersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeakerAdapter.SpeakersViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mSpeakersList.size();
    }

    /**
     * fo
     * Clears current Events list and displays the new list
     *
     * @param speakersList a {@link List} of {@link String}es to be displayed.
     */
    void loadSpeakers(@NonNull List<User> speakersList) {
        this.mSpeakersList.clear();
        this.mSpeakersList.addAll(speakersList);
        notifyDataSetChanged();
    }

    /**
     * Add new events to current list
     *
     * @param speakersList a {@link List} of {@link String}es to be added to current list
     */
    @SuppressWarnings("unused")
    void addFollowers(@NonNull List<User> speakersList) {
        // Removing the events before adding, this ensure no duplication of events
        this.mSpeakersList.removeAll(speakersList);
        this.mSpeakersList.addAll(speakersList);
        notifyDataSetChanged();
    }

    /**
     * {@link android.support.v7.widget.RecyclerView.ViewHolder} object for events item.
     */
    class SpeakersViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFollowerJob;
        private TextView tvFollowerName;


        SpeakersViewHolder(View itemView) {
            super(itemView);
            tvFollowerName = itemView.findViewById(R.id.tv_speaker_name);
            tvFollowerJob = itemView.findViewById(R.id.tv_speaker_job);

        }

        void bind(int position) {

            final Context context = itemView.getContext();

            final User currentEvents = mSpeakersList.get(position);
            // Binding the data
            tvFollowerName.setText(currentEvents.getName());
            tvFollowerJob.setText(currentEvents.getJob());
            // Attaching click listener to each quiz item

        }
    }


}
