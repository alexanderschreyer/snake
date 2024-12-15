package src.gameobjects;

import gui.Window;
import src.enums.Colors;
import src.logic.Grid;
import src.logic.Tile;

public class Apple extends AbstractGameObj {
    private int value;
    
    public Apple(Grid ref) {
        location = assignRandomLocation(ref);
        value = 10;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void draw(Window window) {
        window.setColor(Colors.APPLE.getColor());
        int radius = (location.getTileWidth() / 2);
        window.fillCircle(location.getX() + radius, location.getY() + radius, radius - 2);
    }
}
