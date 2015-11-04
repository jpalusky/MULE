package mule.world.town;

import com.airhacks.afterburner.views.FXMLView;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import mule.KeyHandler;
import mule.TurnManager;
import mule.mainscreen.MainScreen;
import mule.player.Player;
import mule.world.town.store.StoreView;
import mvp.Presenter;

import javax.inject.Inject;
import java.util.HashMap;


public class TownPresenter implements Presenter {
    @Inject private TurnManager turnManager;
    @Inject private KeyHandler keyHandler;
    @Inject private MainScreen mainScreen;

    @FXML private ImageView player;
    @FXML private Pane menuContainer;

    private Image image;

    //constant variables
    final static int STEP_SIZE = 10;
    final static int MAX_BONUS = 250;
    boolean steps = true;
    //constant int for location
    final static int NONE = 0;
    final static int PUB = 1;
    final static int STORE = 2;
    final static int ASSAY = 3;
    final static int LAND = 4;

    private Building pub, store;
    private int[] roundBonus;

    @FXML
    public void initialize() {
        roundBonus = new int[]{50,50,50,100,100,100,100,150,150,150,150,200};
        pub = new Building("Pub", 0, 150, 200, 400);
        store = new Building("Store", 225, 450, 250, 500);
        player.setFocusTraversable(true);
    }

    private int checkLocation(double positionX, double positionY) {
        if (pub.in(positionX, positionY)) return PUB;
        if (store.in(positionX, positionY)) return STORE;

        return NONE;
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

    private boolean isblocked(double x, double y) {
        return y>250 && (x>0 && x<20 || x>145 && x<245 || x>360 && x<460 || x>585 && x<685
                || x>820) || y>400 || y<0;
    }

    private void pubInteraction() {
        gamble();
        turnManager.endTurn();
    }

    private void storeInteraction() {
        HashMap<String, Object> ic = new HashMap<>(1);
        ic.put("parent", menuContainer);
        FXMLView store = new StoreView(ic::get);
        store.getViewAsync(menuContainer.getChildren()::add);
    }

    private void buildingInteract() {
        switch (checkLocation(player.getX(), player.getY())) {
            case PUB:
                pubInteraction();
                break;

            case STORE:
                storeInteraction();
                break;

            default: break;
        }
    }

    private void gamble() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        double timeLeft = turnManager.getTimeLeft();
        int round = turnManager.getRoundNumber();
        int moneyBonus = (int)(roundBonus[round] * timeLeft);
        //do stuff with the moneyBonus
        currentPlayer.addMoney(moneyBonus);
    }

    private boolean isExit(double x1, double y1) {
        double x = x1;
//        double y = y1;
        boolean exit = false;
        if(x<0 || x>860) {
            exit = true;
        }
        return exit;
    }

    public void resetPlayer() {
        player.setX(450);
        player.setY(125);
        menuContainer.getChildren().clear();
    }

    private void movePlayer(int x, int y) {
        if (x > 0 || y > 0) {
            switchStepsAtRight();
        } else {
            switchStepsAtLeft();
        }

        if (!isblocked(player.getX() + x, player.getY() + y)) {
            player.setX(player.getX() + x);
            player.setY(player.getY() + y);
        }
        if (isExit(player.getX() + x, player.getY() + y)) {
            turnManager.getCurrentPlayer().exitTown();
            mainScreen.showMap();
        }
    }

    public void bindInput() {
        // Press X to exit town.
        keyHandler.bind(KeyCode.X, e -> {
            turnManager.getCurrentPlayer().exitTown();
            mainScreen.showMap();
        });

        // Bind movement keys.
        keyHandler.bind(KeyCode.UP, e -> movePlayer(0, -STEP_SIZE));
        keyHandler.bind(KeyCode.DOWN, e -> movePlayer(0, STEP_SIZE));
        keyHandler.bind(KeyCode.LEFT, e -> movePlayer(-STEP_SIZE, 0));
        keyHandler.bind(KeyCode.RIGHT, e -> movePlayer(STEP_SIZE, 0));

        // Bind enter for building interaction
        keyHandler.bind(KeyCode.ENTER, e -> buildingInteract());
    }


    public void unbindInput() {
        keyHandler.unbind(KeyCode.X, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT,
                KeyCode.RIGHT, KeyCode.ENTER);
    }
}

