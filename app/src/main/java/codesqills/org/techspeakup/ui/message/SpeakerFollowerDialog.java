package codesqills.org.techspeakup.ui.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.Rate;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 11/14/2018.
 */

public class SpeakerFollowerDialog extends DialogFragment {

    private static final String TAG = "SpeakerFollowerDialog";
    TextView my_follower_val;
    DatabaseReference reference;
    //create a new bundle and set the arguments to avoid a null pointer
    public SpeakerFollowerDialog() {
        super();
        setArguments(new Bundle());
    }

    //vars
    private String mUserId;
    Followers follow = new Followers();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        mUserId = getArguments().getString(getString(R.string.intent_user_id));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_speaker_follower, container, false);

        Button send = (Button) view.findViewById(R.id.send_notification_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference();

                DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference();
                referenceUser.child("users").child(mUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User userVal=dataSnapshot.getValue(User.class);
                        follow.setmFollowersEmail(userVal.getEmail());
                        follow.setmFollowersName(userVal.getName());
                        follow.setmFollowersJob(userVal.getJob());
                        follow.setmFollowersPic(userVal.getImage());

                        reference
                                .child("followers")
                                .child(mUserId)
                                .setValue(follow);
                        getDialog().dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //
                    }
                });



            }
        });

        return view;
    }

    /**
     * Return the current timestamp in the form of a string
     *
     * @return
     */
    private String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return sdf.format(new Date());
    }


}
