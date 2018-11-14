package codesqills.org.techspeakup.data.remote;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.User;

/**
 * Created by kamalshree on 10/31/2018.
 */

public class FirebaseHandlerImpl implements FirebaseHandler {

    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_PIC = "image";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_TYPE = "type";

    private static final String KEY_USER_KEY = "key";

    private static final String KEY_USER_LOCATION = "location";
    private static final String KEY_USER_JOB = "job";
    private static final String KEY_USER_TWITTER = "twitter";
    private static final String KEY_USER_LINKEDIN = "linkedin";
    private static final String KEY_USER_WEBSITE = "website";
    private static final String KEY_USER_ABOUT = "about";
    private static final String KEY_DEVICE_ID = "deviceid";

    private static final String KEY_LAST_MODIFIED = "eventdate";


    private static final String KEY_NOTIF_PREFS = "prefs";

    private DatabaseReference mUsersRef;
    private DatabaseReference mEventsRef;
    private DatabaseReference mFollowersRef;

    private List<ValueEventListener> mValueListeners;

    // Private variables
    private FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseHandlerImpl() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = firebaseDatabase.getReference();

        mValueListeners = new ArrayList<>();
        mUsersRef = rootRef.child(REF_USERS_NODE);
        mEventsRef = rootRef.child(REF_EVENTS_NODE);
        mFollowersRef = rootRef.child(REF_FOLLOWERS_NODE);


    }

    /*
        @Override
        public void fetchFollowersDetails(String myUid, final Callback<List<User>> callback) {
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot != null) {
                        List<User> eventsList = new ArrayList<>();
                        User singleEvent = snapshot.getValue(User.class);
                        if (singleEvent != null) {
                            singleEvent.setKey(snapshot.getKey());
                            eventsList.add(singleEvent);
                            callback.onReponse(eventsList);
                        } else {
                            callback.onError();
                        }
                    } else {
                        callback.onError();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onError();
                }
            };

            mUsersRef.child(myUid).addValueEventListener(listener);
            mValueListeners.add(listener);
        }

    */
    //Fetch Events by ID
    @Override
    public void fetchEventById(String eventId, final Callback<Events> callback) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    Events singleEvent = snapshot.getValue(Events.class);
                    if (singleEvent != null) {
                        singleEvent.setKey(snapshot.getKey());
                        callback.onReponse(singleEvent);
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        };

        mEventsRef.child(eventId).addValueEventListener(listener);
        mValueListeners.add(listener);
    }


    //Fetch Followers by ID
    @Override
    public void fetchFollowersById(String followersId, final Callback<User> callback) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    User singleEvent = snapshot.getValue(User.class);
                    if (singleEvent != null) {
                        singleEvent.setKey(snapshot.getKey());
                        callback.onReponse(singleEvent);
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        };

        mUsersRef.child(followersId).addValueEventListener(listener);
        mValueListeners.add(listener);
    }

    //Fetch all followers
    @Override
    public void fetchFollowers(final Callback<List<Followers>> callback) {
        if (mCurrentUser == null) {
            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    List<Followers> followersList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        try {
                            Followers singleFollowers = childSnapshot.getValue(Followers.class);
                            singleFollowers.setmKey(childSnapshot.getKey());
                            followersList.add(singleFollowers);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onReponse(followersList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        };


        mFollowersRef.child(mCurrentUser.getUid())
                .addValueEventListener(listener);
    }

    //Fetch all users
    @Override
    public void fetchAllUsers(int limitToFirst, final Callback<List<User>> callback) {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    List<User> userList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        try {
                            User singleEvents = childSnapshot.getValue(User.class);
                            if(childSnapshot.getKey().equals(mCurrentUser.getUid())){
                                //do nothing
                            }
                           else if (singleEvents != null && singleEvents.getName() != null) {
                                singleEvents.setKey(childSnapshot.getKey());
                                userList.add(singleEvents);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onReponse(userList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        };

        Query userRefQuery = mUsersRef.orderByChild(KEY_LAST_MODIFIED);

        // TODO: Implement pagination here.
        if (limitToFirst > 0) {
            userRefQuery.limitToFirst(limitToFirst);
        }
        mUsersRef.addValueEventListener(listener);
        mValueListeners.add(listener);
    }


    //Fetch all events
    @Override
    public void fetchEvents(int limitToFirst, final Callback<List<Events>> callback) {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    List<Events> eventsList = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        try {
                            Events singleEvents = childSnapshot.getValue(Events.class);
                            if (singleEvents != null && singleEvents.getEventName() != null) {
                                singleEvents.setKey(childSnapshot.getKey());
                                eventsList.add(singleEvents);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onReponse(eventsList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        };

        Query eventsRefQuery = mEventsRef.orderByChild(KEY_LAST_MODIFIED);

        // TODO: Implement pagination here.
        if (limitToFirst > 0) {
            eventsRefQuery.limitToFirst(limitToFirst);
        }
        eventsRefQuery.addValueEventListener(listener);
        mValueListeners.add(listener);
    }

    //user Information
    @Override
    public void setUserInfo(User currentUser, final Callback<Void> callback) {
        Map<String, Object> userData = new HashMap<>();
        userData.put(KEY_USER_EMAIL, currentUser.getEmail());
        userData.put(KEY_USER_PIC, currentUser.getImage());
        userData.put(KEY_USER_NAME, currentUser.getName());
        userData.put(KEY_USER_TYPE, currentUser.getType());
        userData.put(KEY_USER_LOCATION, currentUser.getLocation());
        userData.put(KEY_USER_JOB, currentUser.getJob());
        userData.put(KEY_USER_TWITTER, currentUser.getTwitter());
        userData.put(KEY_USER_LINKEDIN, currentUser.getLinkedin());
        userData.put(KEY_USER_WEBSITE, currentUser.getWebsite());
        userData.put(KEY_USER_ABOUT, currentUser.getAbout());

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
            mEventsRef.removeEventListener(listener);

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
