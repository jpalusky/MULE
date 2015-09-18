package presenter;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;

/**
 * Created by Chuanyui on 9/17/15.
 */
public class TownScreenPresenter {
    @FXML private ImageView player;

    private Image image;

    //constant variables
    final static int STEPS = 10;
    boolean steps = true;
    final static double

    @FXML
    private void initialize() {
        player.setFocusTraversable(true);
    }

    @FXML
    public void move(KeyEvent event) {
        double positionX = player.getX();
        double positionY = player.getY();
        //move up
        if (event.getCode() == KeyCode.UP) {
            switchStepsAtRight();   //temporary
            player.setY(positionY-STEPS);
        }
        //move down
        if (event.getCode() == KeyCode.DOWN) {
            switchStepsAtLeft();    //temporary
            player.setY(positionY+STEPS);
        }
        //move left
        if (event.getCode() == KeyCode.LEFT) {
            switchStepsAtLeft();    //walking left animation
            player.setX(positionX-STEPS);
        }
        //move right
        if (event.getCode() == KeyCode.RIGHT) {
            switchStepsAtRight();   //walking right animation
            player.setX(positionX+STEPS);
        }
    }

    private void switchStepsAtRight() {
        if(steps) {
            image = new Image("img/human_walking_right.png");
            steps = false;
        }
        else {
            image = new Image("img/human_walking_right2.png");
            steps = true;
        }
        player.setImage(image);
    }

    private void switchStepsAtLeft() {
        if(steps) {
            image = new Image("img/human_walking_left.png");
            steps = false;
        }
        else {
            image = new Image("img/human_walking_left2.png");
            steps = true;
        }
        player.setImage(image);
    }

    private void checkWall() {

    }
}

