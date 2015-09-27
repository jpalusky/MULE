package mule.mainscreen.statusbar.playerstatus;

import com.airhacks.afterburner.views.FXMLView;

import java.util.function.Function;

public class PlayerStatusView extends FXMLView {
    public PlayerStatusView(Function<String, Object> injectionContext) {
        super(injectionContext);
    }
}
