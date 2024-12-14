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

    /*
     * Handles collision with array bounds, is not
     * (yet) functional with 'collisions' with western
     * and eastern map border
     */
    private void handleBounds(Grid ref) {
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
        drawScore(window);
        drawPlayer(window);
        drawAppendices(window);
    }

    
    private void drawPlayer(Window window) {
        window.setColor(Colors.SNAKE.getColor());
        window.fillRect(location.getX() + 1, location.getY() + 1, location.getTileWidth() - 2, location.getTileHeight() - 2);
    }
    
    private void drawAppendices(Window window) {
        if (appendices != null) {
            for (Tile appendix : appendices) {
                window.setColor(Colors.SNAKE.getColor());
                window.fillRect(appendix.getX() + 1, appendix.getY() + 1, appendix.getTileWidth() - 2, appendix.getTileHeight() - 2);
            }
        }
    }

    private void drawScore(Window window) {
        window.setColor(Colors.FONT.getColor());
        window.setBold(true);;
        window.setFontSize(15);
        window.drawString("SCORE: " + score, 11, 25);
    }

    public void addAppendix(Apple apple) {
        appendices.add(apple.getLocation());
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