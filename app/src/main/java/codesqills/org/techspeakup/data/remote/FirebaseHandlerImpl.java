package codesqills.org.techspeakup.data.remote;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 10/31/2018.
 */

public class FirebaseHandlerImpl implements FirebaseHandler {

    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PIC = "image";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_TYPE = "type";

    private static final String KEY_NOTIF_PREFS = "prefs";

    private DatabaseReference mUsersRef;

    private List<ValueEventListener> mValueListeners;

    // Private variables
    private FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseHandlerImpl() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = firebaseDatabase.getReference();

        mValueListeners = new ArrayList<>();
        mUsersRef = rootRef.child(REF_USERS_NODE);


    }

    @Override
    public void updateUserName(String userName, final Callback<Void> callback) {
        updateUserProperty(KEY_USER_NAME, userName, callback);
    }

    @Override
    public void updateProfilePic(String profilePicUrl, final Callback<Void> callback) {
        updateUserProperty(KEY_USER_PIC, profilePicUrl, callback);
    }

    @Override
    public void setUserInfo(User currentUser, final Callback<Void> callback) {
        Map<String, Object> userData = new HashMap<>();
        userData.put(KEY_USER_EMAIL, currentUser.getEmail());
        userData.put(KEY_USER_PIC, currentUser.getImage());
        userData.put(KEY_USER_NAME, currentUser.getName());
        userData.put(KEY_USER_TYPE, currentUser.getType());

        if (mCurrentUser == null) {
            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        mUsersRef.child(mCurrentUser.getUid()).updateChildren(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onReponse(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError();
                    }
                });
    }

    @Override
    public void destroy() {
        // Remove all listeners
        for (ValueEventListener listener : mValueListeners) {
            mUsersRef.removeEventListener(listener);
        }
    }

    private void updateUserProperty(String property, String value, final Callback<Void> callback) {

        try {
            if (mCurrentUser == null) {
                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            }

            mUsersRef.child(mCurrentUser.getUid()).child(property).setValue(value)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            callback.onReponse(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onError();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        }
    }
}