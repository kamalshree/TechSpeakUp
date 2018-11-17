package codesqills.org.techspeakup.ui.speaker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;


import codesqills.org.techspeakup.R;

/**
 * Created by kamalshree on 11/16/2018.
 */

public class SpeakerFragment extends Fragment {
    private static final String TAG = "CameraFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker, container, false);

        return view;
    }
}
