package mule.world.town;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mule.KeyHandler;
import mule.TurnManager;
import mule.mainscreen.MainScreen;
import mule.player.Player;
import mvp.Presenter;

import javax.inject.Inject;


public class TownPresenter implements Presenter {
    @Inject private TurnManager turnManager;
    @Inject private KeyHandler keyHandler;
    @Inject private MainScreen mainScreen;

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
        pub = new Building("Pub", 0, 225, 250, 500);
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
            if(isExit(positionX, positionY-STEPS)) {
                turnManager.getCurrentPlayer().exitTown();
                mainScreen.showMap();
            }
        }
        //move down
        if (event.getCode() == KeyCode.DOWN) {
            switchStepsAtLeft();    //temporary
            if(!isblocked(positionX, positionY+STEPS)) {
                player.setY(positionY + STEPS);
            }
            if(isExit(positionX, positionY+STEPS)) {
                turnManager.getCurrentPlayer().exitTown();
                mainScreen.showMap();
            }
        }
        //move left
        if (event.getCode() == KeyCode.LEFT) {
            switchStepsAtLeft();    //walking left animation
            if(!isblocked(positionX-STEPS, positionY)) {
                player.setX(positionX - STEPS);
            }
            if(isExit(positionX-STEPS, positionY)) {
                turnManager.getCurrentPlayer().exitTown();
                mainScreen.showMap();
            }
        }
        //move right
        if (event.getCode() == KeyCode.RIGHT) {
            switchStepsAtRight();   //walking right animation
            if(!isblocked(positionX+STEPS, positionY)) {
                player.setX(positionX + STEPS);
            }
            if(isExit(positionX+STEPS, positionY)) {
                turnManager.getCurrentPlayer().exitTown();
                mainScreen.showMap();
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

    private boolean isblocked(double x1, double y1) {
        double x = x1;
        double y = y1;
        boolean blocked = false;
        if((y>250 && ((x>0 && x<20) || (x>145 && x<245) || (x>360 && x<460) || (x>585 && x<685)
                || (x>820))) || y>400 || y<0) {
            blocked = true;
        }
        return blocked;
    }

    private void pubInteraction() {
        gamble();
        //then end turn
        turnManager.endTurn();
        
    }

    private void gamble() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        timeLeft = turnManager.getTimeLeft();
        round = turnManager.getRoundNumber();
        int moneyBonus = (int)(roundBonus[round] * timeLeft);
        //do stuff with the moneyBonus
        currentPlayer.addMoney(moneyBonus);
    }

    private boolean isExit(double x1, double y1) {
        double x = x1;
        double y = y1;
        boolean exit = false;
        if(x<0 || x>900) {
            exit = true;
        }
        return exit;
    }

    public void bindInput() {
        // Press X to exit town.
        keyHandler.bind(KeyCode.X, e -> {
            turnManager.getCurrentPlayer().exitTown();
            mainScreen.showMap();
        });
    }

    public void unbindInput() {
        keyHandler.unbind(KeyCode.X);
    }
}

