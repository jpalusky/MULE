package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import model.Difficulty;
import model.MapType;
import view.Validateable;


/**
 * The Presenter for the ConfigScreen view.
 *
 * @author Kelvin Chen
 */
public class ConfigScreenPresenter implements Presenter, Validateable {
    @FXML private ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML private ChoiceBox<MapType> mapTypeChoiceBox;

    // JavaFX automatically appends a -Controller to the end of
    // the name for some reason. Maybe there is a way to remove this
    // but it is only a minor cosmetic problem.
    @FXML private PlayerSelectPresenter player1Controller;
    @FXML private PlayerSelectPresenter player2Controller;
    @FXML private PlayerSelectPresenter player3Controller;
    @FXML private PlayerSelectPresenter player4Controller;

    @FXML private Button addPlayerButton;
    @FXML private Button removePlayerButton;

    @FXML private Button doneButton;

    private PlayerSelectPresenter[] players;
    private int numPlayers;

    /**
     * Method to run on view initialize.
     */
    @FXML
    private void initialize() {
        difficultyChoiceBox.getItems().setAll(Difficulty.values());
        mapTypeChoiceBox.getItems().setAll(MapType.values());

        numPlayers = 2;
        players = new PlayerSelectPresenter[] { player1Controller, player2Controller,
                player3Controller, player4Controller };

        for (int i = 0; i < 4; ++i) {
            players[i].setPlayerNumber(i + 1);
            if (i > 1) players[i].hide();  // Only show 2 players at start.
        }

        update();
    }

    @FXML
    private void addPlayer() {
        if (numPlayers < 4) players[numPlayers++].show();
        update();
    }

    @FXML
    private void removePlayer() {
        if (numPlayers > 2) players[--numPlayers].hide();
        update();
    }

    /**
     * Update view on some events.
     */
    private void update() {
        removePlayerButton.setManaged(numPlayers > 2);
        removePlayerButton.setVisible(numPlayers > 2);
        addPlayerButton.setManaged(numPlayers < 4);
        addPlayerButton.setVisible(numPlayers < 4);
    }

    @Override
    public boolean isValid() {
        if (difficultyChoiceBox.getValue() == null
                || mapTypeChoiceBox.getValue() == null) return false;

        for (int i = 0; i < numPlayers; ++i) {
            if (!players[i].isValid()) return false;
        }

        return true;
    }

    @FXML
    private void done() {
        System.out.println(isValid());
    }
}
