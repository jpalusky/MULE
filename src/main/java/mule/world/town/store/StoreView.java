package mule.world.town.store;

import com.airhacks.afterburner.views.FXMLView;

import java.util.function.Function;

public class StoreView extends FXMLView {
    public StoreView(Function<String, Object> injectionContext) {
        super(injectionContext);
    }
}
