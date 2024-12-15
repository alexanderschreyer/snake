package src.gameobjects;

import java.util.ArrayList;

import gui.Window;
import src.enums.Colors;
import src.enums.Direction;
import src.logic.Grid;
import src.logic.Tile;

public class Player extends AbstractGameObj {
    private int score;
    private Direction direction;
    private ArrayList<Tile> appendices;
    private ArrayList<Tile> prev;
    private Tile last;

    public Player(Grid ref) {
        location = ref.getStartTile();
        index = ref.getStartTileIndex();
        score = 0;
        appendices = new ArrayList<Tile>();
        direction = Direction.NORTH;
    }

    public ArrayList<Tile> getAppendices() {
        return appendices;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void move(Grid ref) {
        switch (direction) {
            case NORTH:
                moveUp(ref);
                break;
            case WEST:
                moveLeft(ref);
                break;
            case SOUTH:
                moveDown(ref);
                break;
            case EAST:
                moveRight(ref);
                break;
        }
    }

    private void moveLeft(Grid ref) {
        index -= 1;
        handleBounds(ref);
        location = ref.getTiles()[index];
    }

    private void moveRight(Grid ref) {
        index += 1;
        handleBounds(ref);
        location = ref.getTiles()[index];
    }

    private void moveUp(Grid ref) {
        index -= ref.getColumns();
        handleBounds(ref);
        location = ref.getTiles()[index];
    }

    private void moveDown(Grid ref) {
        index += ref.getColumns();
        handleBounds(ref);
        location = ref.getTiles()[index];
    }

    private void handleBounds(Grid ref) {
        // Handles northern and southern map border
        if (!(index >= 0 && index < (ref.getTiles()).length)) {
            int diff = 0;
            if (index < 0) {
                diff = 0 - index;
                index = (ref.getTiles()).length - diff;
            } else if (index > (ref.getTiles()).length - 1) {
                diff = index - (ref.getTiles()).length;
                index = 0 + diff;
            }
        }
    }

    @Override
    public void draw(Window window) {
        drawAppendices(window);
        drawPlayer(window);
        drawScore(window);
    }

    
    private void drawPlayer(Window window) {
        window.setColor(Colors.SNAKE_HEAD.getColor());
        window.fillRect(location.getX(), location.getY(), location.getTileWidth(), location.getTileHeight());
        drawEyes(window);
    }

    private void drawEyes(Window window) {
        int eyeDim = 4;
        int eyeLeftX = 0;
        int eyeLeftY = 0;
        int eyeRightX = 0;
        int eyeRightY = 0;
        if (direction == Direction.NORTH) {
            eyeLeftX = location.getX() + 5;
            eyeLeftY = location.getY() + 4;
            eyeRightX = location.getX() + location.getTileWidth() - 10;
            eyeRightY = location.getY() + 4;
        } else if (direction == Direction.SOUTH) {
            eyeLeftX = location.getX() + 5;
            eyeLeftY = location.getY() + location.getTileHeight() - 8;
            eyeRightX = location.getX() + location.getTileWidth() - 10;
            eyeRightY = location.getY() + location.getTileHeight() - 8;
        } else if (direction == Direction.WEST) {
            eyeLeftX = location.getX() + 4;
            eyeLeftY = location.getY() + 5;
            eyeRightX = location.getX() + 4;
            eyeRightY = location.getY() + location.getTileHeight() - 10;
        } else if (direction == Direction.EAST) {
            eyeLeftX = location.getX() + location.getTileWidth() - 8;
            eyeLeftY = location.getY() + 5;
            eyeRightX = location.getX() + location.getTileWidth() - 8;
            eyeRightY = location.getY() + location.getTileHeight() - 10;
        }
        window.setColor(Colors.SNAKE_EYES.getColor());
        window.fillRect(eyeLeftX, eyeLeftY, eyeDim, eyeDim);
        window.fillRect(eyeRightX, eyeRightY, eyeDim, eyeDim);

    }
    
    private void drawAppendices(Window window) {
        if (appendices != null && appendices.size() > 0) {
            // Draws every appendix except for the last one
            window.setColor(Colors.SNAKE_BODY.getColor());
            for (int i = 0; (i < appendices.size() - 1); i++) {
                Tile appendix = appendices.get(i);
                window.fillRect(appendix.getX(), appendix.getY(), appendix.getTileWidth(), appendix.getTileHeight());
            }
            // Draws the last appendix ('tail')
            window.setColor(Colors.SNAKE_BODY.getColor());
            Tile lastAppendix = appendices.get(appendices.size() - 1);
            window.fillRect(lastAppendix.getX(), lastAppendix.getY(), lastAppendix.getTileWidth(), lastAppendix.getTileHeight());
        }
    }

    public void drawScore(Window window) {
        window.setColor(Colors.FONT.getColor());
        window.setBold(true);;
        window.setFontSize(15);
        window.drawString("SCORE: " + score, 11, 25);
    }

    public void addAppendices(ArrayList<Apple> apples) {
        for (Apple apple : apples) {
            appendices.add(apple.getLocation());
        }
    }

    public void adjustAppendices() {
        if (appendices.size() > 0) {
            prev = new ArrayList<Tile>();
            prev.addAll(appendices);
            appendices.set(0, last);
            if (appendices.size() > 1) {
                for (int i = 1; i < appendices.size(); i++) {
                    appendices.set(i, prev.get(i - 1));
                }
            }
        }
    }

    public void saveLastTile() {
        last = location;
    }
}