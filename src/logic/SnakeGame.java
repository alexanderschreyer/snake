package src.logic;

import gui.Window;
import src.gameobjects.Player;

public class SnakeGame {
    private int width;
    private int height;
    private Grid grid;
    private Player player;

    public SnakeGame (int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Grid(width, height);
        player = new Player(grid.getStartTile(), grid.getTiles());
    }

    public Grid getGrid() {
        return grid;
    }

    /*
     * A method for checking whether the tiles
     * of the grid are placed correctly.
     */
    public void drawTiles(Window window) {
        window.setColor(230,230,230);
        Tile[] tiles = grid.getTiles();
        for (int i = 0; i < tiles.length; i += 2) {
            Tile tile = tiles[i];
            window.fillRect(tile.getX(), tile.getY(), tile.getTileWidth(), tile.getTileHeight());
        }
    }
}
