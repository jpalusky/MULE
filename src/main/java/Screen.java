import javafx.scene.canvas.GraphicsContext;

/**
 * Created by David on 9/9/15.
 */
public interface Screen {
    public void draw(GraphicsContext gc, int width, int height);
}
