package src.gameobjects;

import gui.Window;
import src.logic.Grid;
import src.logic.Tile;

public abstract class AbstractGameObj {
    protected Tile location;
    protected int index;

    abstract void draw(Window window);

    public Tile getLocation() {
        return location;
    }

    public Tile assignRandomLocation(Grid ref) {
        Double randDouble = Math.random() * ref.getColumns() * ref.getColumns();
        int randInt = randDouble.intValue();
        return (ref.getTiles())[randInt];
    }
}
