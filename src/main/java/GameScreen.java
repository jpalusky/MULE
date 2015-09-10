package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by David on 9/9/15.
 */
public class GameScreen implements Screen {
    public GameScreen(Main app, Scene scene) {
        System.out.println("made");
    }
    public void draw(GraphicsContext gc, int width, int height) {
        gc.fillText("this is the game", width / 2, height / 2);
    }
}
