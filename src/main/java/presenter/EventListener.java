package presenter;

import java.util.function.Consumer;

/**
 * An EventListener accepts a eventHandler and uses it to handle events.
 */
public interface EventListener {
    void setHandler(Consumer handler);
}
