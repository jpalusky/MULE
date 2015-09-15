package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The Presenter for the ConfigScreen view.
 *
 * @author Kelvin Chen
 */
public class ConfigScreenPresenter implements Presenter {
    private List<Player> players;

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

    @FXML
    private TextField player2Name;
    @FXML
    private ChoiceBox<Race> player2Race;
    @FXML
    private ChoiceBox<Color> player2Color;

    public ConfigScreenPresenter() {
        players = new ArrayList<>();
    }

    @Override
    public void initialize() {
        difficultyChoiceBox.getItems().setAll(Difficulty.values());
        mapTypeChoiceBox.getItems().setAll(MapType.values());

        player1Race.getItems().setAll(Race.values());
        player1Color.getItems().setAll(Color.values());

        player2Race.getItems().setAll(Race.values());
        player2Color.getItems().setAll(Color.values());
    }
}
