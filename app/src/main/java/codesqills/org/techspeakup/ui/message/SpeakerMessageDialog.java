package codesqills.org.techspeakup.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Message;
import codesqills.org.techspeakup.data.models.Rate;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * Created by kamalshree on 11/14/2018.
 */

public class SpeakerMessageDialog extends DialogFragment {

    private static final String TAG = "SpeakerMessageDialog";
    TextView my_rate_val;
    //create a new bundle and set the arguments to avoid a null pointer
    public SpeakerMessageDialog() {
        super();
        setArguments(new Bundle());
    }


    //widgets
    RatingBar mRate;

    //vars
    private String mUserId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        mUserId = getArguments().getString(getString(R.string.intent_user_id));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_speaker_rate, container, false);
        mRate = (RatingBar) view.findViewById(R.id.speaker_ratebar);
        my_rate_val = (TextView) view.findViewById(R.id.my_rate_val);

        addListenerOnRatingBar();
        //if rating value is changed,
        //display the current rating value in the result (textview) automatically

        Button send = (Button) view.findViewById(R.id.send_notification_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                    //create the new message
                    Rate rate = new Rate();
                    rate.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    rate.setRate(String.valueOf(mRate.getRating()));

                        reference
                                .child("rate")
                                .child(mUserId)
                                .setValue(rate);
                        getDialog().dismiss();
                        Toast.makeText(getActivity(), getResources().getString(R.string.speaker_rate_success), Toast.LENGTH_SHORT).show();

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


    public void addListenerOnRatingBar() {

        mRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                my_rate_val.setText(String.valueOf(rating));
            }
        });

    }

}
