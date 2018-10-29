package codesqills.org.techspeakup.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by kamalshree on 10/25/2018.
 */

public interface BasePresenter {

    void start(@Nullable Bundle extras);

    void destroy();
}
