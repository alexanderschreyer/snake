package src.gameobjects;

import gui.Window;
import src.enums.Colors;
import src.logic.Tile;

public class Food extends AbstractGameObj {
    protected int value;

    public Food(Tile location) {
        this.location = location;
        value = 10;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void draw(Window window) {
        window.setColor(Colors.FOOD.getColor());
        // left
        window.fillRect(location.getX() + 3, location.getY() + 9, 5, location.getTileHeight() - 18);
        // right
        window.fillRect(location.getX() + location.getTileWidth() - 8, location.getY() + 9, 5, location.getTileHeight() - 18);
        // top
        window.fillRect(location.getX() + 9, location.getY() + 3, location.getTileWidth() - 18, 5);
        // bottom
        window.fillRect(location.getX() + 9, location.getY() + location.getTileHeight() - 8, location.getTileWidth() - 18, 5);
    }
}
