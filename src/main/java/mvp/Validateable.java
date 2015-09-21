package mvp;

/**
 * A interface that allows you to check if a view is valid or not.
 *
 * The View's Presenter should implement this interface.
 */
public interface Validateable {
    /**
     * Check if all user input on a View is valid.
     *
     * @return whether or not the View is valid.
     */
    boolean isValid();
}
