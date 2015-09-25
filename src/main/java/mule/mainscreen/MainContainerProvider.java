package mule.mainscreen;

import javafx.scene.layout.Pane;

import javax.inject.Provider;

public class MainContainerProvider implements Provider<Pane> {
    private Pane mainContainer;

    public Pane get() {
        return mainContainer;
    }

    public void set(Pane mainContainer) {
        this.mainContainer = mainContainer;
    }
}
