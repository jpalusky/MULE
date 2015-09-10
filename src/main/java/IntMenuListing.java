package sample;

import java.util.ArrayList;

/**
 * Created by David on 9/9/15.
 */
public class IntMenuListing implements MenuListing {
    String text;
    int value;
    int max;
    public IntMenuListing() {
        text = "";
        value = 0;
        max = -1;
    }
    public IntMenuListing(String text, int max) {
        this.text = text;
        value = 0;
        this.max = max;
    }
    public MenuItem getCurrent() {
        return new MenuItem(" " + text, value);
    }
    public int getValue() {
        return this.value;
    }
    public void next() {
        if (max < 0 || value < max) {
            value++;
        }
    }
    public void prev() {
        if (value > 0) {
            value--;
        }
    }
}
