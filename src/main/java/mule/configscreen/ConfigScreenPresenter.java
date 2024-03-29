package mule.configscreen;

import com.airhacks.afterburner.injection.Injector;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mule.Difficulty;
import mule.GameState;
import mule.KeyHandler;
import mule.configscreen.loadscreen.LoadScreenView;
import mule.configscreen.playerselect.PlayerSelectPresenter;
import mule.configscreen.playerselect.PlayerSelectView;
import mule.mainscreen.MainScreenView;
import mule.player.Color;
import mule.player.Player;
import mule.player.Race;
import mule.world.map.MapType;
import mvp.Presenter;
import mvp.Validateable;

import javax.inject.Inject;


/**
 * The Presenter for the ConfigScreen view.
 *
 * @author Kelvin Chen
 */
public class ConfigScreenPresenter implements Presenter, Validateable {
    @Inject private Stage primaryStage;
    @Inject private KeyHandler keyHandler;

    @FXML private ChoiceBox<Difficulty> difficultyChoiceBox;
    @FXML private ChoiceBox<MapType> mapTypeChoiceBox;

    @FXML private VBox playerContainer;

    private PlayerSelectPresenter[] players;

    @FXML private Button addPlayerButton;
    @FXML private Button removePlayerButton;

    private int numPlayers;

    @Override
    public void initialize() {
        difficultyChoiceBox.getItems().setAll(Difficulty.values());
        mapTypeChoiceBox.getItems().setAll(MapType.values());

        numPlayers = 2;

        players = new PlayerSelectPresenter[4];
        for (int i = 0; i < 4; ++i) {
            PlayerSelectView playerView = new PlayerSelectView();
            PlayerSelectPresenter player = (PlayerSelectPresenter) playerView.getPresenter();

            playerContainer.getChildren().add(playerView.getView());
            player.setPlayerNumber(i + 1);
            if (i >= numPlayers) player.hide();
            players[i] = player;
        }

        update();
    }

    @FXML
    public void addPlayer() {
        if (numPlayers < 4) players[numPlayers++].show();
        update();
    }

    @FXML
    public void removePlayer() {
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
    public void done() {
        if (isValid()) {
            Player[] ps = new Player[numPlayers];
            for (int i = 0; i < numPlayers; ++i) {
                ps[i] = players[i].getPlayer();
                ps[i].setDefaultResources(difficultyChoiceBox.getValue());
            }

            GameState gameState = new GameState(ps,
                    difficultyChoiceBox.getValue(), mapTypeChoiceBox.getValue());
            Injector.setModelOrService(gameState.getClass(), gameState);

            new MainScreenView().getViewAsync(view -> {
                Scene scene = new Scene(view);
                scene.addEventHandler(EventType.ROOT, keyHandler::handle);
                primaryStage.setScene(scene);
            });
        }
    }

    @FXML
    public void skip() {
        Player[] players = {
                new Player("Player 1", Color.BLUE, Race.HUMAN),
                new Player("Player 2", Color.RED, Race.BONZOID),
        };
        Difficulty d = Difficulty.STANDARD;
        players[0].setDefaultResources(d);
        players[1].setDefaultResources(d);
        GameState gameState = new GameState(players, d, MapType.STANDARD);
        Injector.setModelOrService(gameState.getClass(), gameState);
        new MainScreenView().getViewAsync(view -> {
            Scene scene = new Scene(view);
            scene.addEventHandler(EventType.ROOT, keyHandler::handle);
            primaryStage.setScene(scene);
        });
    }

    public void load(ActionEvent actionEvent) {
        new LoadScreenView().getViewAsync(view -> {
            Scene scene = new Scene(view);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });
    }
}
