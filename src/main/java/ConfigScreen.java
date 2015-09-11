import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import java.util.ArrayList;

/**
 * Created by David on 9/9/15.
 */
public class ConfigScreen implements Screen {
    Application app;
    TextMenuListing difficulty;
    TextMenuListing mapType;
    IntMenuListing players;
    MenuListing currentListing;
    ArrayList<MenuListing> menuListings;
    int index;

    Image muleL;
    Image muleR;
    int muleCount = 500;
    boolean right = true;
    int counter = 0;

    public ConfigScreen(Main app, Scene scene) {
        this.app = app;
        difficulty = new TextMenuListing("Beginner", "Intermediate", "Tournament");
        mapType = new TextMenuListing("Standard", "Random");
        players = new IntMenuListing("players", 4);
        currentListing = players;

        menuListings = new ArrayList<MenuListing>();
        menuListings.add(difficulty);
        menuListings.add(players);
        menuListings.add(mapType);
        index = 0;

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT) {
                menuListings.get(index).next();
            } else if (code == KeyCode.LEFT) {
                menuListings.get(index).prev();
            } else if (code == KeyCode.UP) {
                if (index > 0) {
                    index--;
                }
            } else if (code == KeyCode.DOWN || code == KeyCode.TAB) {
                if (index < menuListings.size() - 1) {
                    index++;
                }
            } else if (code == KeyCode.ENTER) {
                app.switchScreen(new PlayerSelectScreen(players.getValue(), 0, app, scene));
            }

        });
    }
    public void draw(GraphicsContext gc, int width, int height) {
        int center = width / 2;
        gc.setFont(Font.font("Arial", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("M.U.L.E.", center, 50);
        gc.fillText("Select difficulty", center, 110);
        if (menuListings.get(index) == difficulty) gc.setFill(Color.GREEN);
        gc.fillText(difficulty.toString(), center, 150);
        gc.setFill(Color.BLACK);
        gc.fillText("Select players", center, 230);
        if (menuListings.get(index) == players) gc.setFill(Color.GREEN);
        gc.fillText(players.getCurrent().toString(), center, 270);
        gc.setFill(Color.BLACK);
        gc.fillText("Select players", center, 300);
        if (menuListings.get(index) == mapType) gc.setFill(Color.GREEN);
        gc.fillText(mapType.getCurrent().getText(), center, 330);
        gc.setFill(Color.BLACK);
        muleL = new Image("file:src/main/resources//muleLeft.png");
        muleR = new Image("file:src/main/resources//muleRight.png");
        if(counter < 30) {
            gc.drawImage(muleR, muleCount--, 340, 100, 100);
            right = false;
            System.out.println("Right");
        }
        else if (counter < 60) {
            gc.drawImage(muleL, muleCount--, 340, 100, 100);
            right = true;
            System.out.println("Left");
        }
        else {
            counter = 0;
            gc.drawImage(muleR, muleCount--, 340, 100, 100);
        }
        counter++;
        if(muleCount == 0) {
            muleCount = 600;
        }
    }

}
