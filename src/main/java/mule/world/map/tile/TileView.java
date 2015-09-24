package mule.world.map.tile;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;

public class TileView extends FXMLView {
    private TileType type;

    public TileView(TileType type) {
        this.type = type;
    }

    @Override
    public Parent getView() {
        Parent view = super.getView();
        String c = "";
        switch (type) {
            case PLAIN:
                c = "plain";
                break;
            case RIVER:
                c = "river";
                break;
            case MOUNTAIN1:
                c = "mountain1";
                break;
            case MOUNTAIN2:
                c = "mountain2";
                break;
            case MOUNTAIN3:
                c = "mountain3";
                break;
            case TOWN:
                c = "town";
                break;
        }
        if (!view.getStyleClass().contains(c))
            view.getStyleClass().add(c);
        return view;
    }

    @Override
    public TilePresenter getPresenter() {
        TilePresenter presenter = (TilePresenter) super.getPresenter();
        presenter.setTileType(type);
        return presenter;
    }
}
