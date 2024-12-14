package src.gameobjects;

import gui.Window;
import src.enums.Colors;
import src.logic.Grid;
import src.logic.Tile;

public class Apple extends AbstractGameObj {
    
    public Apple(Grid ref) {
        location = assignRandomLocation(ref);
    }

    private Tile assignRandomLocation(Grid ref) {
        Double randDouble = Math.random() * ref.getColumns() * ref.getColumns();
        int randInt = randDouble.intValue();
        return (ref.getTiles())[randInt];
    }

    @Override
    public void draw(Window window) {
        window.setColor(Colors.APPLE.getColor());
        int radius = (location.getTileWidth() / 2);
        window.fillCircle(location.getX() + radius, location.getY() + radius, radius - 2);
    }
}
