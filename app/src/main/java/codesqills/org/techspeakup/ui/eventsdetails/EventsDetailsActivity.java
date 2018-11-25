package codesqills.org.techspeakup.ui.eventsdetails;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.utils.NetworkUtils;
import codesqills.org.techspeakup.widgets.EventWidget;

/**
 * Created by kamalshree on 11/7/2018.
 */

public class EventsDetailsActivity extends AppCompatActivity implements EventsDetailsContract.View, View.OnClickListener {
    private static final String TAG = "EventsDetails";

    private EventsDetailsContract.Presenter mPresenter;
    private Bundle extras;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;

    @BindView(R.id.tv_eventdetail_eventname)
    TextView eventDetailsName;

    @BindView(R.id.tv_event_details_eventsubTitle)
    TextView eventDetailsSubName;

    @BindView(R.id.tv_event_details_eventdate)
    TextView eventDetailsDate;

    @BindView(R.id.tv_event_details_eventlocation)
    TextView eventDetailsLocation;

    @BindView(R.id.tv_event_details_eventdetails)
    TextView eventDetailsDetails;

    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;


    @BindView(R.id.toolbar_speakerprofile)
    View toolbar_event;


    String eventName, eventLocation, eventDate;
    NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        PresenterInjector.injectEventsDetailsPresenter(this);
        intialiseUI();
        check_connection();

        extras = getIntent().getExtras();

        eventName = extras.getString(EventsDetailsContract.KEY_EVENTS_NAME);
        eventLocation = extras.getString(EventsDetailsContract.KEY_EVENTS_LOCATION);
        eventDate = extras.getString(EventsDetailsContract.KEY_EVENTS_DATE);
        SharedPrefsDetails(eventName, eventLocation, eventDate);

        mPresenter.start(getIntent().getExtras());
    }

    /* Check Internet Connection */
    public void check_connection() {
        networkUtils = new NetworkUtils(this);
        networkUtils.execute();
    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.speaker_event_details));
        mBack.setOnClickListener(this);
    }

    @Override
    public void setPresenter(EventsDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        //Show loading here
        Log.d(TAG, "It is Loading");
    }

    @Override
    public void hideLoading() {
        //Hide loading here
        Log.d(TAG, "It Loading stopped");
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
            default:
                break;
        }
    }

    @Override
    public void loadEventName(String eventName) {

        eventDetailsSubName.setText(eventName);
        eventDetailsName.setText(eventName);
    }

    @Override
    public void loadEventDate(String eventDate) {
        eventDetailsDate.setText(eventDate);
    }

    @Override
    public void loadEventLocation(String eventLocation) {
        eventDetailsLocation.setText(eventLocation);
    }

    @Override
    public void loadEventDetails(String eventDetails) {
        eventDetailsDetails.setText(eventDetails);
    }


    //saving details in Shared Preference for App Widget
    public void SharedPrefsDetails(String eventName, String eventLocation, String eventDate) {

        Intent intent = new Intent(this, EventWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);


        SharedPreferences sharedPref = getSharedPreferences("EventDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SharedPrefeventName", eventName);
        editor.putString("SharedPrefeventLocation", eventLocation);
        editor.putString("SharedPrefeventDate", eventDate);
        editor.apply();
    }
}
