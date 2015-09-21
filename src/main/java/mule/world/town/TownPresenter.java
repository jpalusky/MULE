package mule.world.town;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Chuanyui on 9/17/15.
 */
public class TownPresenter {
    @FXML private ImageView player;

    private Image image;

    //constant variables
    final static int STEPS = 10;
    boolean steps = true;

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
            if(!isblocked(positionX, positionY-STEPS)) {
                player.setY(positionY - STEPS);
            }
        }
        //move down
        if (event.getCode() == KeyCode.DOWN) {
            switchStepsAtLeft();    //temporary
            if(!isblocked(positionX, positionY+STEPS)) {
                player.setY(positionY + STEPS);
            }
        }
        //move left
        if (event.getCode() == KeyCode.LEFT) {
            switchStepsAtLeft();    //walking left animation
            if(!isblocked(positionX-STEPS, positionY)) {
                player.setX(positionX - STEPS);
            }
        }
        //move right
        if (event.getCode() == KeyCode.RIGHT) {
            switchStepsAtRight();   //walking right animation
            if(!isblocked(positionX+STEPS, positionY)) {
                player.setX(positionX + STEPS);
            }
        }
    }

    private void switchStepsAtRight() {
        if(steps) {
            image = new Image("mule/world/town/human_walking_right.png");
            steps = false;
        }
        else {
            image = new Image("mule/world/town/human_walking_right2.png");
            steps = true;
        }
        player.setImage(image);
    }

    private void switchStepsAtLeft() {
        if(steps) {
            image = new Image("mule/world/town/human_walking_left.png");
            steps = false;
        }
        else {
            image = new Image("mule/world/town/human_walking_left2.png");
            steps = true;
        }
        player.setImage(image);
    }

    private boolean isblocked(double x1, double y1) {
        double x = x1 + 20;
        double y = y1 + 40;
        boolean blocked = false;
        if((y>200 && ((x>0 && x<40) || (x>110 && x<180) || (x>260 && x<320) || (x> 410 && x <470)
                || (x>560))) || y>360) {
            blocked = true;
        }
        return blocked;
    }
}

