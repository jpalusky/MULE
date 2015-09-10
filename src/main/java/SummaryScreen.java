package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * Created by David on 9/10/15.
 */
public class SummaryScreen implements Screen {
    public SummaryScreen(Main app, Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.ENTER) {
                app.switchScreen(new GameScreen(app, scene));
            }
        });
    }
    public void draw(GraphicsContext gc, int width, int height) {
        gc.fillText("this is the summary", width / 2, height / 2);
    }
}
