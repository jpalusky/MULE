package view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import model.Difficulty;
import model.MapType;
import presenter.ConfigScreenPresenter;
import presenter.Presenter;

/**
 * The controller for the ConfigScreen scene.
 *
 * @author Kelvin Chen
 */
public class ConfigScreenView implements View {
    private Presenter presenter;

    @FXML
    private ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML
    private ChoiceBox<MapType> mapTypeChoiceBox;

    public void initialize() {
        presenter = new ConfigScreenPresenter(this);
        difficultyChoiceBox.getItems().setAll(Difficulty.values());
        mapTypeChoiceBox.getItems().setAll(MapType.values());
    }
}
