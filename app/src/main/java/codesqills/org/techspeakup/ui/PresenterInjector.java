package codesqills.org.techspeakup.ui;

import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfileContract;
import codesqills.org.techspeakup.ui.editprofile.SpeakerEditProfilePresenter;
import codesqills.org.techspeakup.ui.events.EventsContract;
import codesqills.org.techspeakup.ui.events.EventsPresenter;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsContract;
import codesqills.org.techspeakup.ui.eventsdetails.EventsDetailsPresenter;
import codesqills.org.techspeakup.ui.followers.FollowersContract;
import codesqills.org.techspeakup.ui.followers.FollowersPresenter;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsContract;
import codesqills.org.techspeakup.ui.followersdetails.FollowersDetailsPresenter;
import codesqills.org.techspeakup.ui.home.HomeContract;
import codesqills.org.techspeakup.ui.home.HomePresenter;
import codesqills.org.techspeakup.ui.newnotification.NewNotificationContract;
import codesqills.org.techspeakup.ui.newnotification.NewNotificationPresenter;
import codesqills.org.techspeakup.ui.notificationFollowers.NotificationFollowersContract;
import codesqills.org.techspeakup.ui.notificationFollowers.NotificationFollowersPresenter;
import codesqills.org.techspeakup.ui.notificationsend.NotificationSendContract;
import codesqills.org.techspeakup.ui.notificationsend.NotificationSendPresenter;
import codesqills.org.techspeakup.ui.profile.ProfileContract;
import codesqills.org.techspeakup.ui.profile.ProfilePresenter;
import codesqills.org.techspeakup.ui.signin.SignInContract;
import codesqills.org.techspeakup.ui.signin.SignInPresenter;
import codesqills.org.techspeakup.ui.speakerprofile.SpeakerProfileContract;
import codesqills.org.techspeakup.ui.speakerprofile.SpeakerProfilePresenter;

/**
 * Created by kamalshree on 10/25/2018.
 */

public class PresenterInjector {

    public static void injectSignInPresenter(SignInContract.View signInView) {
        new SignInPresenter(signInView);
    }

    public static void injectProfilePresenter(ProfileContract.View profileView) {
        new ProfilePresenter(profileView);
    }

    public static void injectHomePresenter(HomeContract.View homeeView) {
        new HomePresenter(homeeView);
    }

    public static void injectSpeakerProfilePresenter(SpeakerProfileContract.View SpeakerProfileView) {
        new SpeakerProfilePresenter(SpeakerProfileView);
    }

    public static void injectSpeakerEditProfilePresenter(SpeakerEditProfileContract.View SpeakerEditProfileView) {
        new SpeakerEditProfilePresenter(SpeakerEditProfileView);
    }

    public static void injectEventsPresenter(EventsContract.View EventsProfileView) {
        new EventsPresenter(EventsProfileView);
    }

    public static void injectEventsDetailsPresenter(EventsDetailsContract.View EventsDetailsProfileView) {
        new EventsDetailsPresenter(EventsDetailsProfileView);
    }

    public static void injectFollowersPresenter(FollowersContract.View FollowersProfileView) {
        new FollowersPresenter(FollowersProfileView);
    }

    public static void injectFollowersDetailsPresenter(FollowersDetailsContract.View FollowersDetailsProfileView) {
        new FollowersDetailsPresenter(FollowersDetailsProfileView);
    }

    public static void injectNewNotificationPresenter(NewNotificationContract.View NewNotificationProfileView) {
        new NewNotificationPresenter(NewNotificationProfileView);

    }
    public static void injectNotificationFollowersPresenter(NotificationFollowersContract.View NotificationFollowerProfileView) {
        new NotificationFollowersPresenter(NotificationFollowerProfileView);
    }

    public static void injectNotificationSendPresenter(NotificationSendContract.View NotificationSendProfileView) {
        new NotificationSendPresenter(NotificationSendProfileView);
    }
}
