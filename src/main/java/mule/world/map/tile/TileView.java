package mule.world.map.tile;

import com.airhacks.afterburner.views.FXMLView;

import java.util.function.Function;

public class TileView extends FXMLView {
    public TileView(Function<String, Object> injectionContext) {
        super(injectionContext);
    }
}
