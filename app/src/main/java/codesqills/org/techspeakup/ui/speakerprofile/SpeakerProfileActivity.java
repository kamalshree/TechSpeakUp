package codesqills.org.techspeakup.ui.speakerprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Rate;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileActivity;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/4/2018.
 */

public class SpeakerProfileActivity extends AppCompatActivity implements SpeakerProfileContract.View, View.OnClickListener {

    private SpeakerProfileContract.Presenter mPresenter;

    @BindView(R.id.speaker_profile_tv_name)
    TextView mUsername;

    @BindView(R.id.speaker_profile_tv_map_val)
    TextView mUserlocation;

    @BindView(R.id.speaker_profile_tv_designation)
    TextView mUserjob;

    @BindView(R.id.tv_twitter_val)
    TextView mUsertwitter;

    @BindView(R.id.iv_linkedin_val)
    TextView mUserlinkedin;

    @BindView(R.id.iv_link_val)
    TextView mUserlink;

    @BindView(R.id.tv_about_val)
    TextView mUserabout;

    @BindView(R.id.speaker_profile_tv_followers_val)
    TextView mFollowerCount;

    @BindView(R.id.speaker_profile_tv_event_val)
    TextView mEventCount;

    @BindView(R.id.tv_event_details_val)
    TextView mEventDetails;

    @BindView(R.id.speaker_profile_tv_rate_val)
    TextView mRateCount;

    @BindView(R.id.speaker_profile_iv)
    ImageView mProfileImage;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    private Bundle extras;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;

    @BindView(R.id.refresh)
    Button refreshBtn;

    @BindView(R.id.relative_layout)
    RelativeLayout relative_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_profile);
        ButterKnife.bind(this);

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }
        intialiseUI();
        PresenterInjector.injectSpeakerProfilePresenter(this);

        extras = getIntent().getExtras();
        mPresenter.start(extras);

        checkNewProfile();
    }

    private void intialiseUI() {
        mBack.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
        loadFollowerCount();
        getFollowerCount();
        loadRateCount();
        getRateCount();


    }

    private void checkNewProfile() {
        if (mUserlocation.getText().toString().matches("") && mUsertwitter.getText().toString().matches("") && mUserlinkedin.getText().toString().matches("") && mUserlink.getText().toString().matches("") && mUserabout.getText().toString().matches("")) {
            buildDialog(this).show();
        }
    }

    @Override
    public void setPresenter(SpeakerProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadProfileImage(String picUrl) {
        if (picUrl == null) {
            return;
        }
        Glide.with(getApplicationContext()).load(picUrl).apply(RequestOptions.circleCropTransform()).into(mProfileImage);
    }

    @Override
    public void loadUserName(String userName) {
        mUsername.setText(userName);
    }

    @Override
    public void loadUserLocation(String location) {
        mUserlocation.setText(location);
    }

    @Override
    public void loadUserJob(String job) {
        mUserjob.setText(job);
    }

    @Override
    public void loadUserTwitter(String twiiter) {
        mUsertwitter.setText(twiiter);

    }

    @Override
    public void loadUserLinkedin(String linkedin) {
        mUserlinkedin.setText(linkedin);
    }

    @Override
    public void loadUserWebsite(String website) {
        mUserlink.setText(website);

    }

    @Override
    public void loadUserAbout(String about) {
        mUserabout.setText(about);
    }

    @Override
    public void loadUserEventCount(String eventCount) {

        mEventCount.setText(eventCount);
    }

    @Override
    public void loadUserEventDetails(String eventDetails) {
        mEventDetails.setText(eventDetails);
    }

    public void loadFollowerCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("followers_count")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        mFollowerCount.setText(snapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
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


    //Set Rate count in USER node

    public void loadRateCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("rate_count")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        mRateCount.setText(snapshot.getValue().toString());

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
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        double rateval = 0;
                        int size = (int) dataSnapshot.getChildrenCount();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            try {
                                Rate singleEvents = childSnapshot.getValue(Rate.class);
                                rateval += Double.parseDouble(singleEvents.getRate());
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void checkInternet() {
        if (NetworkUtils.connectionStatus(this)) {
            relative_layout.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        relative_layout.setVisibility(View.INVISIBLE);
        layout_internet.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.VISIBLE);
        refreshBtn.setVisibility(View.VISIBLE);
    }

    /* Profile not updated Dialog */
    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(getResources().getString(R.string.speaker_profile_dialog_title));
        builder.setMessage(getResources().getString(R.string.speaker_profile_dialog_message));

        builder.setPositiveButton(getString(R.string.no_interent_okbutton), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent editprofile = new Intent(getApplication(), SpeakerEditProfileActivity.class);
                startActivity(editprofile);
            }

        });

        return builder;
    }


}
