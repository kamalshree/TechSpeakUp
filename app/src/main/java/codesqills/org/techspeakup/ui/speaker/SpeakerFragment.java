package codesqills.org.techspeakup.ui.speaker;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;


import java.util.List;

import codesqills.org.techspeakup.R;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.PresenterInjector;
import codesqills.org.techspeakup.ui.followers.FollowersActivity;
import codesqills.org.techspeakup.ui.followers.FollowersAdapter;
import codesqills.org.techspeakup.ui.message.MessageDialog;
import codesqills.org.techspeakup.ui.message.SpeakerFollowerDialog;
import codesqills.org.techspeakup.ui.message.SpeakerMessageDialog;

/**
 * Created by kamalshree on 11/16/2018.
 */

public class SpeakerFragment extends Fragment implements SpeakerContract.View,SpeakerAdapter.SpeakerItemListener {

    private SpeakerContract.Presenter mPresenter;
    private static final String TAG = "SpeakerFragment";
    private Bundle extras;
    private static final int BACK_PRESS_DURATION = 3000;

    private RecyclerView mSpeakersRecyclerView;
    private SpeakerAdapter mSpeakerAdapter;

    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker, container, false);
        PresenterInjector.injectSpeakerPresenter(this);
        extras = getActivity().getIntent().getExtras();
        mPresenter.start(extras);

        //RecyclerView
        mSpeakersRecyclerView = view.findViewById(R.id.all_speaker_list);
        mSpeakersRecyclerView.setHasFixedSize(true);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        mSpeakersRecyclerView.setLayoutManager(linearLayoutManager);

        mSpeakerAdapter = new SpeakerAdapter(this,getActivity());
        mSpeakersRecyclerView.setAdapter(mSpeakerAdapter);

        swipeRefreshLayout = view.findViewById(R.id.showfeedback_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent,
                R.color.colorAccent, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.start(getActivity().getIntent().getExtras());

                swipeRefreshLayout.setRefreshing(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSpeakerAdapter != null) {
                            mSpeakerAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, BACK_PRESS_DURATION);
            }
        });
        return view;
    }


    @Override
    public void setPresenter(SpeakerContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadSpeakerDetails(List<User> speakers) {
        mSpeakersRecyclerView.setVisibility(View.VISIBLE);
        mSpeakerAdapter.loadSpeakers(speakers);
    }

    @Override
    public void showRateDialog(User user) {
        SpeakerMessageDialog dialog = new SpeakerMessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_user_id), user.getKey());
        dialog.setArguments(bundle);
        dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_message));
    }

@Override
    public void showFollowerDialog(User user) {
        SpeakerFollowerDialog dialog = new SpeakerFollowerDialog();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.intent_user_id), user.getKey());
        dialog.setArguments(bundle);
        dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_message));
    }

    @Override
    public void onSpeakerClicked(User user) {
        mPresenter.onSpeakerClicked(user);
    }

 @Override
    public void onSpeakerFollowerClicked(User user) {
        mPresenter.onSpeakerFollowerClicked(user);
    }
}
