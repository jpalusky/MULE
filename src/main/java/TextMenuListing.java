package sample;

import java.util.ArrayList;

/**
 * Created by David on 9/9/15.
 */
public class TextMenuListing implements MenuListing {
    ArrayList<MenuItem> items;
    int index;
    public TextMenuListing() {
        items = new ArrayList<MenuItem>();
        index = 0;
    }
    public TextMenuListing(String... items) {
        this.items = new ArrayList<MenuItem>();
        for (int i = 0, index = 0; i < items.length; i++) {
            this.items.add(new MenuItem(items[i], i));
        }
    }
    public TextMenuListing(ArrayList<String> items) {
        this.items = new ArrayList<MenuItem>();
        for (int i = 0; i < items.size(); i++) {
            this.items.add(new MenuItem(items.get(i), i));
        }
        index = 0;
    }
    public void add(MenuItem item) {
        this.items.add(item);
    }
    public void add(String str) {
        this.items.add(new MenuItem(str, this.items.size()));
    }
    public MenuItem getCurrent() {
        return items.get(index);
    }
    public String toString() {
        String left = "  ";
        String right = "  ";
        if (index < items.size() - 1) {
            right = " >";
        }
        if (index > 0) {
            left = "< ";
        }
        return left + items.get(index).getText() + right;
    }
    public void next() {
        if (index + 1 < items.size()) {
            index++;
        }
    }
    public void prev() {
        if (index > 0) {
            index--;
        }
    }
}
