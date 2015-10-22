package mule;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Global input handler.
 */
public class KeyHandler {
    private HashMap<KeyCode, Consumer<KeyEvent>> eventMap;

    public KeyHandler() {
        eventMap = new HashMap<>();
    }

    public void handle(Event e) {
        if (e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) e;

            if (ke.getEventType() == KeyEvent.KEY_PRESSED
                    && eventMap.containsKey(ke.getCode())) {
                eventMap.get(ke.getCode()).accept(ke);
            }
        }
    }

    public void bind(KeyCode code, Consumer<KeyEvent> event) {
        eventMap.put(code, event);
    }

    public void unbind(KeyCode code) {
        eventMap.remove(code);
    }

    public void unbind(KeyCode... codes) {
        for (KeyCode code : codes) {
            unbind(code);
        }
    }

    public void unbindAll() {
        eventMap = new HashMap<>();
    }
}
