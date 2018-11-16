package codesqills.org.techspeakup.ui.events;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.Events;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsActivity;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsContract;
import codesqills.org.techspeakup.utils.NetworkUtils;

/**
 * Created by kamalshree on 11/6/2018.
 */

public class EventsActivity extends AppCompatActivity implements EventAdapter.EventsItemListener, EventsContract.View, View.OnClickListener {

    @BindView(R.id.speaker_profile_page_toolbar_settings)
    TextView editProfile;

    private RecyclerView mEventsRecyclerView;
    private EventAdapter mEventsAdapter;
    private EventsContract.Presenter mPresenter;
    private Bundle extras;
    private static final int BACK_PRESS_DURATION = 3000;


    @BindView(R.id.speaker_profile_cardviewone)
    CardView mCard;

    @BindView(R.id.speaker_profile_page_back)
    ImageView mBack;

    @BindView(R.id.layout_internet)
    View layout_internet;

    @BindView(R.id.toolbar_speakerprofile)
    View toolbar_event;

    @BindView(R.id.tv_no_internet)
    TextView noInternet;
    @BindView(R.id.refresh)
    Button refreshBtn;


    @BindView(R.id.home_screen_pb)
    LottieAnimationView loading;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_event);
        ButterKnife.bind(this);
        intialiseUI();

        if (!NetworkUtils.connectionStatus(this)) {
            ShowNoInternetMessage();
            buildDialog(this).show();
        }

        PresenterInjector.injectEventsPresenter(this);
        extras = getIntent().getExtras();
        mPresenter.start(extras);

        setUpSwipeRefresh();

    }

    private void intialiseUI() {
        editProfile.setText(getResources().getString(R.string.speaker_event_profile));
        mBack.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);

        //RecyclerView
        mEventsRecyclerView = findViewById(R.id.recyclerview_events);
        mEventsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mEventsRecyclerView.setLayoutManager(linearLayoutManager);

        mEventsAdapter = new EventAdapter(this);
        mEventsRecyclerView.setAdapter(mEventsAdapter);
    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.refresh_eventscreen);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent,
                R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.start(EventsActivity.this.getIntent().getExtras());

                swipeRefreshLayout.setRefreshing(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mEventsAdapter != null) {
                            mEventsAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, BACK_PRESS_DURATION);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @Override
    public void onEventsClicked(Events events) {
        mPresenter.onEventsClicked(events);
    }

    @Override
    public void setPresenter(EventsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void loadEvents(List<Events> events) {
        mEventsRecyclerView.setVisibility(View.VISIBLE);
        mEventsAdapter.loadEvents(events);
    }

    @Override
    public void loadEventsError() {
        //show error
    }

    @Override
    public void navigateToEventsDesc(Events events) {
        Intent eventsDetailsIntent = new Intent(this, EventsDetailsActivity.class);
        eventsDetailsIntent.putExtra(EventsDetailsContract.KEY_EVENTS_ID, events.getKey());
        eventsDetailsIntent.putExtra(EventsDetailsContract.KEY_EVENTS_NAME, events.getEventName());
        eventsDetailsIntent.putExtra(EventsDetailsContract.KEY_EVENTS_LOCATION, events.getEventLocation());
        eventsDetailsIntent.putExtra(EventsDetailsContract.KEY_EVENTS_DATE, events.getEventDate());
        startActivity(eventsDetailsIntent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.anim_nothing);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

}
