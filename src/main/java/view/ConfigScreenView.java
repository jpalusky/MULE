package view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Color;
import model.Difficulty;
import model.MapType;
import model.Race;
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

    @FXML
    private TextField player1Name;
    @FXML
    private ChoiceBox<Race> player1Race;
    @FXML
    private ChoiceBox<Color> player1Color;

    public void initialize() {
        presenter = new ConfigScreenPresenter(this);

        difficultyChoiceBox.getItems().setAll(Difficulty.values());
        mapTypeChoiceBox.getItems().setAll(MapType.values());

        player1Race.getItems().setAll(Race.values());
        player1Color.getItems().setAll(Color.values());
    }
}
