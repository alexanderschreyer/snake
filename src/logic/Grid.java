package src.logic;

public class Grid {
    private static final int ROWS = 25;

    private int columns;
    private Tile[] tiles;

    Grid(int width, int height) {
        setColsAndRows(width, height);
        createTiles(width, height);
    }

    private void setColsAndRows(int width, int height) {
        if (width == height) {
            columns = ROWS;
        } else {
            Double calc = ((double)ROWS / 9 * 16);
            columns = calc.intValue();
        }
    }

    private void createTiles(int width, int height) {
        if (ROWS == columns) {
            tiles = new Tile[(ROWS * ROWS)];
            int tileSide = (width / ROWS);
            int count = 0;
            int y = 0;
            for (int i = 1; i <= ROWS; i++) {
                int x = 0;
                for (int j = 1; j <= ROWS; j++) {
                    Tile tile = new Tile(j, i, tileSide, x, y);
                    tiles[count] = tile;
                    x += tileSide;
                    count++;
                }
                y += tileSide;
            }
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return ROWS;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Tile getStartTile() {
        return tiles[tiles.length / 2];
    }
}
