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

/**
 * Created by kamalshree on 11/14/2018.
 */

public class DeleteMessageDialog extends DialogFragment {

    private static final String TAG = "DeleteMessageDialog";

    //create a new bundle and set the arguments to avoid a null pointer
    public DeleteMessageDialog() {
        super();
        setArguments(new Bundle());
    }

    //vars
    private String mMessageId;
    //widgets
    TextView mMessage;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        mMessageId = getArguments().getString(getString(R.string.intent_message_id));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_message, container, false);
        mMessage = (EditText) view.findViewById(R.id.message);

        Button delete = view.findViewById(R.id.delete_notification_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                reference
                        .child("notifications")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(mMessageId)
                        .removeValue();
                getDialog().dismiss();
                    Toast.makeText(getActivity(), "message deleted", Toast.LENGTH_SHORT).show();


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
