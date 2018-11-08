package codesqills.org.techspeakup.ui.eventsdetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.ui.PresenterInjector;

/**
 * Created by kamalshree on 11/7/2018.
 */

public class EventsDetailsActivity extends AppCompatActivity implements EventsDetailsContract.View,View.OnClickListener{

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        PresenterInjector.injectEventsDetailsPresenter(this);
        intialiseUI();
        extras = getIntent().getExtras();
        mPresenter.start(getIntent().getExtras());
    }
    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.speaker_event_details));
        mBack.setOnClickListener(this);
    }
    @Override
    public void setPresenter(EventsDetailsContract.Presenter presenter) {
        this.mPresenter=presenter;
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
}
