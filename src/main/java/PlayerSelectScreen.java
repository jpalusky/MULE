package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Created by David on 9/9/15.
 */
public class PlayerSelectScreen implements Screen {
    int player;
    ArrayList<MenuListing> menuListings;
    TextMenuListing name;
    TextMenuListing color;
    TextMenuListing race;
    int index;
    int cursorCount;
    String cursor;

    public PlayerSelectScreen(int numPlayers, int currPlayer, Main app, Scene scene) {
        name = new TextMenuListing("");
        race = new TextMenuListing("Human", "Flapper");
        color = new TextMenuListing("Blue", "Red", "Yellow");
        menuListings = new ArrayList<MenuListing>();
        menuListings.add(race);
        menuListings.add(name);
        menuListings.add(color);
        index = 0;
        cursorCount = 0;
        cursor = " ";
        this.player = currPlayer;
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.ENTER) {
                if (currPlayer == numPlayers) {
                    app.switchScreen(new SummaryScreen(app, scene));
                } else {
                    app.switchScreen(new PlayerSelectScreen(numPlayers, this.player + 1, app, scene));
                }
            } else if (code == KeyCode.UP) {
                if (index > 0) {
                    index--;
                }
            } else if (code == KeyCode.DOWN || code == KeyCode.TAB) {
                if (index < menuListings.size() -1) {
                    index++;
                }
            } else if (code == KeyCode.LEFT) {
                menuListings.get(index).prev();
            } else if (code == KeyCode.RIGHT) {
                menuListings.get(index).next();
            } else if (code == KeyCode.BACK_SPACE) {
                name.getCurrent().removeLast();
            } else if (menuListings.get(index) == name) {
                name.getCurrent().append(event.getText());
            }
        });
    }

    public void draw(GraphicsContext gc, int width, int height) {
        int center = width / 2;
        gc.fillText("Player: " + (player + 1), center, 30);
        gc.fillText("Choose a race", center, 100);
        if (isCurrent(race)) {
            gc.setFill(Color.GREEN);
        }
        gc.fillText(race.getCurrent().getText(), center, 140);
        gc.setFill(Color.BLACK);
        gc.fillText("Choose a name", center, 200);
        if (isCurrent(name)) {
            gc.setFill(Color.GREEN);
            cursor = "_";
        } else {
            cursor = " ";
        }
        gc.fillText(name.getCurrent().getText() + ((cursorCount > 30) ? cursor : " "), center, 240);
        gc.setFill(Color.BLACK);
        gc.fillText("Choose a color", center, 320);
        if (isCurrent(color)) {
            gc.setFill(Color.GREEN);
        }
        gc.fillText(color.getCurrent().getText(), center, 360);
        gc.setFill(Color.BLACK);
        cursorCount++;
        if(cursorCount > 60) {
            cursorCount = 0;
        }
    }

    public boolean isCurrent(MenuListing item) {
        return this.menuListings.get(index) == item;
    }
}
