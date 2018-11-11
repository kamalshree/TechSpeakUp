package codesqills.org.techspeakup.ui.profile;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import codesqills.org.techspeakup.ui.BasePresenter;
import codesqills.org.techspeakup.ui.BaseView;

/**
 * Created by kamalshree on 10/29/2018.
 */

public interface ProfileContract {



    interface View extends BaseView<Presenter>{
        void loadUserPic(String picUrl);

        void loadUserName(String userName);

        void loadEmailAddress(String emailAddress);

        void loadUserType(String userType);

        void onPictureUploadError();

        void onSaveError();

        void onProfileSaved();
    }

    interface Presenter extends BasePresenter{
        void saveProfile(@Nullable Bitmap picture, String userType);

        void saveProfile(@Nullable String pictureUrl, String username,String userType);

    }

}


