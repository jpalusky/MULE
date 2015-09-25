package mule.world.map;

import mule.world.map.tile.TileType;
import mule.world.map.tile.TileView;

/**
 * The world map.
 *
 * Coordinates start with (0, 0) in the top left corner.
 */
public class Map {
    private boolean initialized;
    private TileView[][] tiles;

    public void initializeMap(TileType[][] map) {
        if (initialized) return;
        tiles = new TileView[5][9];
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 5; ++y) {
                TileView tile = new TileView(map[y][x]);
                tiles[y][x] = tile;
            }
        }
        initialized = true;
    }

    public TileView[][] getTiles() {
        return tiles;
    }

    public TileView getTile(int x, int y) {
        return  tiles[y][x];
    }
}
