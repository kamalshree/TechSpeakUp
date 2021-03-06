package codesqills.org.techspeakup.ui.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v4.app.DialogFragment;

import java.util.Locale;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kamalshree on 11/14/2018.
 */

public class MessageDialog extends DialogFragment {

    private static final String TAG = "MessageDialog";

    //create a new bundle and set the arguments to avoid a null pointer
    public MessageDialog() {
        super();
        setArguments(new Bundle());
    }


    //widgets
    EditText mMessage;

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
        View view = inflater.inflate(R.layout.dialog_message, container, false);
        mMessage = view.findViewById(R.id.message);

        Button send = view.findViewById(R.id.send_notification_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                if (!mMessage.getText().toString().equals("")) {

                    //create the new message
                    Message message = new Message();
                    message.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    message.setMessage(mMessage.getText().toString());
                    message.setTimestamp(getTimestamp());

                    //insert the new message
                    reference
                            .child("notifications")
                            .child(mUserId)
                            .child(reference.push().getKey())
                            .setValue(message);
                    getDialog().dismiss();
                    Toast.makeText(getActivity(), getResources().getString(R.string.send_notification_sent_message), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.send_notification_message_enter), Toast.LENGTH_SHORT).show();
                }

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
