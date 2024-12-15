package src.gameobjects;

import gui.Window;
import src.enums.Colors;
import src.logic.Tile;

public class Apple extends Food {
    private static final int BASE_VALUE = 100;
    private final int START_RADIUS = ((location.getTileWidth() / 2));
    
    private int adjustedRadius;
    private int stage;
    
    public Apple(Tile location) {
        super(location);
        adjustedRadius = START_RADIUS - 1;
        value = BASE_VALUE;
        stage = 1;
    }

    @Override
    public void draw(Window window) {
        window.setColor(Colors.FOOD.getColor());
        window.fillCircle(location.getX() + START_RADIUS, location.getY() + START_RADIUS, adjustedRadius);
    }

    public void decreaseValue() {
        stage += 1;
        switch (stage) {
            case 2:
                value = 75;
                adjustedRadius = 10;
                break;
            case 3:
                value = 50;
                adjustedRadius = 8;
                break;
            case 4:
                value = 25;
                adjustedRadius = 6;
                break;
            case 5:
                value = 0;
                adjustedRadius = 0;
                break;
        }
    }
}
