package codesqills.org.techspeakup.ui.speaker;

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

import butterknife.BindView;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 11/18/2018.
 */

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.SpeakersViewHolder> {

    private List<User> mSpeakersList;
    private Context context;
    private SpeakerAdapter.SpeakerItemListener mSpeakerItemListener;


    SpeakerAdapter(@NonNull SpeakerAdapter.SpeakerItemListener speakersItemListener, Context context) {
        mSpeakersList = new ArrayList<>();
        this.context = context;
        this.mSpeakerItemListener = speakersItemListener;
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
        @BindView(R.id.tv_speaker_job)
        TextView tvFollowerJob;
        @BindView(R.id.tv_speaker_name)
        TextView tvFollowerName;
        @BindView(R.id.iv_star)
        ImageView ivStar;
        @BindView(R.id.iv_follow)
        ImageView ivfollow;

        SpeakersViewHolder(View itemView) {
            super(itemView);
            tvFollowerName = itemView.findViewById(R.id.tv_speaker_name);
            tvFollowerJob = itemView.findViewById(R.id.tv_speaker_job);
            ivStar = itemView.findViewById(R.id.iv_star);
            ivfollow = itemView.findViewById(R.id.iv_follow);

        }

        void bind(int position) {

            final Context context = itemView.getContext();

            final User currentEvents = mSpeakersList.get(position);
            // Binding the data
            tvFollowerName.setText(currentEvents.getName());
            tvFollowerJob.setText(currentEvents.getJob());
            // Attaching click listener to each quiz item
            ivStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSpeakerItemListener.onSpeakerClicked(currentEvents);
                }
            });

            ivfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSpeakerItemListener.onSpeakerFollowerClicked(currentEvents);
                }
            });
        }
    }

    /**
     * Callback interface for listening to click Messages on Messages items
     */
    interface SpeakerItemListener {
        /**
         * Called when Messages is clicked
         *
         * @param user the Messages that was clicked
         */
        void onSpeakerClicked(User user);

        void onSpeakerFollowerClicked(User user);
    }

}
