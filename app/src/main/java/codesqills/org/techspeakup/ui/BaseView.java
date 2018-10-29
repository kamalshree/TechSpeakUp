package codesqills.org.techspeakup.ui;

/**
 * Created by kamalshree on 10/25/2018.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoading();

    void hideLoading();
}
