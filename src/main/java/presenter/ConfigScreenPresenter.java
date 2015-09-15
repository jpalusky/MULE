package presenter;

import view.View;

/**
 * The Presenter for the Config screen.
 */
public class ConfigScreenPresenter implements Presenter {
    private View view;

    public ConfigScreenPresenter(View view) {
        this.view = view;
    }
}
