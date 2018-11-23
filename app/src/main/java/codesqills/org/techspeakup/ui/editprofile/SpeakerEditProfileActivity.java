package codesqills.org.techspeakup.ui.editprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/5/2018.
 */

public class SpeakerEditProfileActivity extends AppCompatActivity implements SpeakerEditProfileContract.View, View.OnClickListener {

    private Bundle extras;
    SpeakerEditProfileContract.Presenter mPresenter;

    @BindView(R.id.speaker_editprofile_name)
    EditText editName;
    @BindView(R.id.speaker_editprofile_location)
    EditText editLocation;
    @BindView(R.id.speaker_editprofile_job)
    EditText editJob;
    @BindView(R.id.speaker_editprofile_twitter)
    EditText editTwitter;
    @BindView(R.id.speaker_editprofile_linkedin)
    EditText editLinkedin;
    @BindView(R.id.speaker_editprofile_website)
    EditText editWebsite;
    @BindView(R.id.speaker_editprofile_aboutme)
    EditText editAboutMe;
    @BindView(R.id.speaker_editprofile_eventcount)
    EditText editEventCount;
    @BindView(R.id.speaker_editprofile_eventdetails)
    EditText editEventDetails;
    @BindView(R.id.speaker_editprofile_submit)
    Button editSubmit;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;

    @BindView(R.id.speaker_editprofile_follower)
    TextView mFollowerCount;

    @BindView(R.id.speaker_editprofile_rate)
    TextView mRateCount;


    @BindView(R.id.tv_no_internet)
    TextView noInternet;

    @BindView(R.id.refresh)
    Button refreshBtn;
    @BindView(R.id.toolbar_speakerprofile)
    View toolbar_event;
    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.speaker_profile_cardviewone)
    CardView cardView;

    //String deviceid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_editprofile);
        ButterKnife.bind(this);

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }

        intialiseUI();
        PresenterInjector.injectSpeakerEditProfilePresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);
    }

    private void intialiseUI() {
        //deviceid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        editProfile.setText(getResources().getString(R.string.speaker_editprofile_profile));
        mBack.setOnClickListener(this);
        editSubmit.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
        getFollowerCount();
        getRateCount();
    }

    @Override
    public void setPresenter(SpeakerEditProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setEditName(String name) {
        if (name == null) {
            return;
        }
        editName.setText(name);
    }

    @Override
    public void setEditLocation(String location) {
        if (location == null) {
            return;
        }
        editLocation.setText(location);
    }

    @Override
    public void setEditJob(String job) {
        if (job == null) {
            return;
        }
        editJob.setText(job);
    }

    @Override
    public void setEditTwitter(String twiiter) {
        if (twiiter == null) {
            return;
        }
        editTwitter.setText(twiiter);
    }

    @Override
    public void setEditLinkedin(String linkedin) {
        if (linkedin == null) {
            return;
        }
        editLinkedin.setText(linkedin);
    }

    @Override
    public void setEditWebsite(String website) {
        if (website == null) {
            return;
        }
        editWebsite.setText(website);
    }

    @Override
    public void setEditAbout(String about) {
        if (about == null) {
            return;
        }
        editAboutMe.setText(about);
    }


    @Override
    public void setEditEventCount(String eventCount) {
        if (eventCount == null) {
            return;
        }
        editEventCount.setText(eventCount);
    }


    @Override
    public void setEditEventDetails(String eventDetails) {
        if (eventDetails == null) {
            return;
        }
        editEventDetails.setText(eventDetails);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speaker_editprofile_submit:
                if (editName.getText().toString().trim().equals("")) {
                    editName.setError(getResources().getString(R.string.user_name_empty_error));
                } else if (editAboutMe.getText().toString().trim().equals("")) {
                    editAboutMe.setError(getResources().getString(R.string.user_name_empty_error));
                } else {
                    mPresenter.saveEditProfile(editName.getText().toString(),
                            editLocation.getText().toString(),
                            editJob.getText().toString(),
                            editTwitter.getText().toString(),
                            editLinkedin.getText().toString(),
                            editWebsite.getText().toString(),
                            editAboutMe.getText().toString(),
                            mFollowerCount.getText().toString(),
                            mRateCount.getText().toString(),
                            editEventCount.getText().toString(),
                            editEventDetails.getText().toString()
                    );
                }

                break;
            case R.id.speaker_profile_page_back:
                onBackPressed();
                break;

            case R.id.refresh:
                checkInternet();
                break;
            default:
                break;
        }
    }

    public void getFollowerCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("followers")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        int size = (int) dataSnapshot.getChildrenCount();
                        mFollowerCount.setText("" + size);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getRateCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("rate")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        double rateval = 0;
                        int size = (int) dataSnapshot.getChildrenCount();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            try {
                                User singleEvents = childSnapshot.getValue(User.class);
                                rateval += Double.parseDouble(singleEvents.getRateCount());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        double speakerRateVal = rateval / size;
                        String stringdouble = Double.toString(speakerRateVal);
                        mRateCount.setText(stringdouble);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    @Override
    public void onProfileSaved() {
        showSuccessDialog();
        //Toast.makeText(this, getString(R.string.speaker_editprofile_saved_successfully), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveError() {
        Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.sucess_title))
                .setMessage(getString(R.string.sucess_message))
                .setNegativeButton(getString(R.string.sucess_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create().show();
    }

    private void checkInternet() {
        if (NetworkUtils.connectionStatus(this)) {
            cardView.setVisibility(View.VISIBLE);
            toolbar_event.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        cardView.setVisibility(View.INVISIBLE);
        layout_internet.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.VISIBLE);
        refreshBtn.setVisibility(View.VISIBLE);
    }

    /* No Internet Dialog */
    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(getString(R.string.no_internet_title));
        builder.setMessage(getString(R.string.no_internet_message));

        builder.setPositiveButton(getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        return builder;
    }
}
