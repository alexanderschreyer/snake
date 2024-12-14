package src.gameobjects;

import gui.Window;
import src.logic.Tile;

public abstract class AbstractGameObj {
    protected Tile location;
    protected int index;

    abstract void draw(Window window);

    public Tile getLocation() {
        return location;
    }
}
