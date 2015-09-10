package sample;

/**
 * Created by David on 9/9/15.
 */
public class MenuItem {
    String text;
    int value;
    public MenuItem(String text, int value) {
        this.text = text;
        this.value = value;
    }
    public String toString() {
        return this.value + this.text;
    }
    public String getText() {
        return this.text;
    }
    public void append(String toAppend) {
        this.text = this.text + toAppend;
    }
    public void removeLast() {
        int len = this.text.length();
        if (len > 0) {
            this.text = this.text.substring(0, len - 1);
        }
    }
}
