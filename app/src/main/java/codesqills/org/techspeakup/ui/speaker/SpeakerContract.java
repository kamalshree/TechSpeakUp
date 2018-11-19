package codesqills.org.techspeakup.ui.speaker;

import java.util.List;

import codesqills.org.techspeakup.data.models.Followers;
import codesqills.org.techspeakup.data.models.User;
import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 11/18/2018.
 */

public interface SpeakerContract {

    interface View extends BaseView<Presenter> {
        void loadSpeakerDetails(List<User> speakers);
    }

    interface Presenter extends BasePresenter {
    }
}
