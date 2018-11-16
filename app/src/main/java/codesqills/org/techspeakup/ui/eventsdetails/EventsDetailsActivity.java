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

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.toolbar_speakerprofile)
    View toolbar_event;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;
    @BindView(R.id.refresh)
    Button refreshBtn;

    String eventName,eventLocation,eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        PresenterInjector.injectEventsDetailsPresenter(this);
        intialiseUI();
        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }
        extras = getIntent().getExtras();

        eventName = extras.getString(EventsDetailsContract.KEY_EVENTS_NAME);
        eventLocation = extras.getString(EventsDetailsContract.KEY_EVENTS_LOCATION);
        eventDate = extras.getString(EventsDetailsContract.KEY_EVENTS_DATE);
        SharedPrefsDetails(eventName,eventLocation,eventDate);

        mPresenter.start(getIntent().getExtras());
    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.speaker_event_details));
        mBack.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }

    @Override
    public void setPresenter(EventsDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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


    private void checkInternet() {
        if (NetworkUtils.connectionStatus(this)) {
            mCard.setVisibility(View.VISIBLE);
            toolbar_event.setVisibility(View.VISIBLE);
        } else {
            ShowNoInternetMessage();
        }
    }

    /*Action when internet not available */
    private void ShowNoInternetMessage() {
        mCard.setVisibility(View.INVISIBLE);
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

    //saving details in Shared Preference for App Widget
    public void SharedPrefsDetails(String eventName,String eventLocation,String eventDate){

        Intent intent = new Intent(this, EventWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);


        SharedPreferences sharedPref = getSharedPreferences("EventDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString( "SharedPrefeventName", eventName);
        editor.putString( "SharedPrefeventLocation", eventLocation);
        editor.putString( "SharedPrefeventDate", eventDate);
        editor.apply();
    }
}
