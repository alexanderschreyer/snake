package src.gameobjects;

import gui.Window;
import src.enums.Colors;
import src.logic.Grid;

public class Apple extends Food {
    
    public Apple(Grid ref) {
        super(ref);
        value = 50;
    }

    @Override
    public void draw(Window window) {
        window.setColor(Colors.FOOD.getColor());
        int radius = (location.getTileWidth() / 2);
        window.fillCircle(location.getX() + radius, location.getY() + radius, radius - 2);
    }
}
