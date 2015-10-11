package mule.world.town;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mule.RoundManager;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;


public class TownPresenter implements Presenter {
    @Inject private RoundManager roundManager;

    @FXML private ImageView player;

    private Image image;

    //constant variables
    final static int STEPS = 10;
    final static int MAX_BONUS = 250;
    boolean steps = true;
    //constant int for location
    final static int NONE = 0;
    final static int PUB = 1;
    final static int STORE = 2;
    final static int ASSAY = 3;
    final static int LAND = 4;

    //Constructor
    Building pub;
    int[] roundBonus;

    //Variables
    double timeLeft;   //these 2 need to pass in
    int round;

    @FXML
    public void initialize() {
        roundBonus = new int[]{50,50,50,100,100,100,100,150,150,150,150,200};
        pub = new Building("Pub", 0, 150, 200, 400);
        player.setFocusTraversable(true);
    }

    @FXML
    public void move(KeyEvent event) {
        double positionX = player.getX();
        double positionY = player.getY();
        //move up
        if (event.getCode() == KeyCode.UP) {
            switchStepsAtRight();   //temporary
            if(!isBlocked(positionX, positionY-STEPS)) {
                player.setY(positionY - STEPS);
            }
        }
        //move down
        if (event.getCode() == KeyCode.DOWN) {
            switchStepsAtLeft();    //temporary
            if(!isBlocked(positionX, positionY+STEPS)) {
                player.setY(positionY + STEPS);
            }
        }
        //move left
        if (event.getCode() == KeyCode.LEFT) {
            switchStepsAtLeft();    //walking left animation
            if(!isBlocked(positionX-STEPS, positionY)) {
                player.setX(positionX - STEPS);
            }
        }
        //move right
        if (event.getCode() == KeyCode.RIGHT) {
            switchStepsAtRight();   //walking right animation
            if(!isBlocked(positionX+STEPS, positionY)) {
                player.setX(positionX + STEPS);
            }
        }

        //interact
        if (event.getCode() == KeyCode.ENTER) {
            //check player location before interact
            switch(checkLocation(positionX,positionY)) {
                case PUB:   pubInteraction();
                            break;

                case NONE:  break;

                default:    break;
            }
        }
    }

    private int checkLocation(double positionX, double positionY) {
        //if player is at a pub
        if(positionX > pub.getxMin() && positionX < pub.getxMax()
                && positionY > pub.getyMin() && positionY < pub.getyMax()) {
            return PUB;
        }
        //more location in here!!
        else {
            return NONE;
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

    private boolean isBlocked(double x1, double y1) {
        double x = x1 + 20;
        double y = y1 + 40;
        boolean blocked = false;
        if((y>200 && ((x>0 && x<40) || (x>110 && x<180) || (x>260 && x<320) || (x> 410 && x <470)
                || (x>560))) || y>360) {
            blocked = true;
        }
        return blocked;
    }

    private void pubInteraction() {
        gamble();
        //then end turn
        roundManager.getTurnManager().endTurn();
        
    }

    private void gamble() {
        Player currentPlayer = roundManager.getTurnManager().getCurrentPlayer();
        timeLeft = roundManager.getTurnManager().getTimeLeft();
        round = roundManager.getRoundNumber();
        int moneyBonus = (int)(roundBonus[round] * timeLeft);
        //do stuff with the moneyBonus
        currentPlayer.addMoney(moneyBonus);
    }
}

