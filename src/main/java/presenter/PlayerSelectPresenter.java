package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Color;
import model.Player;
import model.Race;
import view.Validateable;

/**
 * Presenter for the PlayerSelect menu.
 */
public class PlayerSelectPresenter implements Presenter, Validateable {
    @FXML private Label playerNumberLabel;
    @FXML private ChoiceBox<Race> race;
    @FXML private ChoiceBox<Color> color;
    @FXML private TextField name;

    @FXML
    private void initialize() {
        race.getItems().setAll(Race.values());
        color.getItems().setAll(Color.values());
    }

    public String getName() {
        return name.getText();
    }

    public Race getRace() {
        return race.getValue();
    }

    public Color getColor() {
        return color.getValue();
    }

    public void setPlayerNumber(int n) {
        playerNumberLabel.setText(playerNumberLabel.getText().replace("#", "" + n));
    }

    public void hide() {
        playerNumberLabel.setVisible(false);
        playerNumberLabel.setManaged(false);
    }

    public void show() {
        playerNumberLabel.setVisible(true);
        playerNumberLabel.setManaged(true);
    }

    @Override
    public boolean isValid() {
        return !(getName().isEmpty() || getRace() == null || getColor() == null);
    }

    /**
     * Construct and return a new Player with the values from the Presenter.
     *
     * @return a new Player model or null if the input are not valid.
     */
    public Player getPlayer() {
        return isValid() ? new Player(getName(), getColor(), getRace())
                : null;
    }
}
