package mule.world.map;

import mule.world.map.tile.Tile;
import mule.world.map.tile.TileType;

/**
 * The world map.
 *
 * Coordinates start with (0, 0) in the top left corner.
 */
public class Map {
    private Tile[][] tiles;
    private boolean initialized;

    public void initialize(TileType[][] map) {
        if (!initialized) {
            tiles = new Tile[map.length][map[0].length];
            for (int i = 0; i < map.length; ++i) {
                for (int j = 0; j < map[i].length; ++j) {
                    tiles[i][j] = new Tile(map[i][j], j, i);
                }
            }
            initialized = true;
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(Point p) {
        return getTile(p.x, p.y);
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public static TileType[][] getStandardMap() {
        TileType R = TileType.RIVER;
        TileType P = TileType.PLAIN;
        TileType M1 = TileType.MOUNTAIN1;
        TileType M2 = TileType.MOUNTAIN2;
        TileType M3 = TileType.MOUNTAIN3;
        return new TileType[][] {
                {P, P, M1, P, R, P, M3, P, P},
                {P, M1, P, P, R, P, P, P, M3},
                {M3, P, P, P, TileType.TOWN, P, P, P, M1},
                {P, M2, P, P, R, P, M2, P, P},
                {P, P, M2, P, R, P, P, P, M2}
        };
    }

    public static TileType[][] getRandomMap() {
        TileType[][] map = new TileType[5][9];

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                map[i][j] = getRandomTile();

                // The river is in the center of the map.
                if (j == 4) map[i][j] = TileType.RIVER;

                // Town is in the center of the map.
                if (i == 2 && j == 4) map[i][j] = TileType.TOWN;
            }
        }

        return map;
    }

    private static TileType getRandomTile() {
        double p = Math.random();

        if (p < .5) return TileType.PLAIN;
        if (p < .75) return TileType.MOUNTAIN1;
        if (p < .875) return TileType.MOUNTAIN2;
        return TileType.MOUNTAIN3;
    }
}
