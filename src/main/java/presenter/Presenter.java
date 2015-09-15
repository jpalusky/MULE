package presenter;

/**
 * The main interface for a Presenter to implement.
 */
public interface Presenter {
    /**
     * Initializes the JavaFX controller. JavaFX is designed for MVC,
     * but this project uses MVP, so in this situation, the Presenter
     * serves as a Controller.
     */
    void initialize();
}
