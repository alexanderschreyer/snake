package src.gameobjects;

import gui.Window;
import src.enums.Colors;
import src.logic.Grid;

public class Obstacle extends AbstractGameObj {
    private String imgSrc = "lib/images/cactus_icon.png";

    public Obstacle(Grid ref) {
        location = assignRandomLocation(ref);
    }

    @Override
    public void draw(Window window) {
        int scaleFactor = location.getTileWidth() / 11;
        window.drawImage(imgSrc, location.getX(), location.getY(), scaleFactor);
    }
}
