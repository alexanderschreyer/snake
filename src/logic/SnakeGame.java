package src.logic;

import gui.Window;
import src.gameobjects.Player;
import src.enums.Colors;
import src.enums.Direction;
import src.gameobjects.Apple;

public class SnakeGame {
    private int width;
    private int height;
    private Grid grid;
    private Player player;
    private Apple apple;
    private int steps;
    
    private boolean justEaten;
    private int countOne;
    private int countTwo;

    private boolean gameOver = false;

    public SnakeGame (int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Grid(width, height);
        initializeGameState();
    }

    public Grid getGrid() {
        return grid;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    private void initializeGameState() {
        player = new Player(grid);
        apple = new Apple(grid);
        steps = 0;
        countOne = 0;
        countTwo = 0;
        gameOver = false;
    }

    public void handleEvents(Window window) {
        handleInput(window);
        if (!gameOver) {
            step();
        }
    }

    public void handleInput(Window window) {
        if (window.isKeyPressed("escape")) {
            window.close();
        }
        if (window.isKeyPressed("w") && player.getDirection() != Direction.SOUTH) {
            player.setDirection(Direction.NORTH);
        }
        if (window.isKeyPressed("a") && player.getDirection() != Direction.EAST) {
            player.setDirection(Direction.WEST);
        }
        if (window.isKeyPressed("s") && player.getDirection() != Direction.NORTH) {
            player.setDirection(Direction.SOUTH);
        }
        if (window.isKeyPressed("d") && player.getDirection() != Direction.WEST) {
            player.setDirection(Direction.EAST);
        }
        if (window.isKeyPressed("r")  && gameOver) {
            initializeGameState();
        }
    }

    public void step() {
        steps += 1;
        if (steps % 10 == 0) {
            player.saveLastTile();
            player.move(grid);
            player.adjustAppendices();
            if (justEaten) {
                countTwo += 1;
            }
            if (justEaten == true && countOne == countTwo) {
                player.addAppendix(apple);
                apple = new Apple(grid);
                justEaten = false;
                countTwo = 0;
            }
            checkApple();
            checkCollision();
        }
    }

    private void checkApple() {
        String playerLoc = (player.getLocation()).getTileName();
        String appleLoc = (apple.getLocation()).getTileName();
        if (playerLoc.equals(appleLoc)) {
            player.setScore(player.getScore() + 50);
            countOne += 1;
            justEaten = true;
        }
    }

    private void checkCollision() {
        String playerLoc = (player.getLocation()).getTileName();
        for (Tile appendix : player.getAppendices()) {
            if (appendix.getTileName().equals(playerLoc)) {
                player.setScore(player.getScore() +5000);
                gameOver = true;
            }
        }
    }

    /*
     * A method for checking whether the tiles
     * of the grid are placed correctly.
     */
    public void drawTiles(Window window) {
        window.setColor(245,245,245);
        Tile[] tiles = grid.getTiles();
        for (int i = 0; i < tiles.length; i += 2) {
            Tile tile = tiles[i];
            window.fillRect(tile.getX(), tile.getY(), tile.getTileWidth(), tile.getTileHeight());
        }
    }

    public void drawGame(Window window) {
        // drawTiles(window);
        drawBackground(window);
        apple.draw(window);
        player.draw(window);
        if (gameOver) {
            drawGameOver(window);
        }
    }

    private void drawGameOver(Window window) {
        window.setColor(Colors.FONT.getColor());
        window.setFontSize(50);
        window.drawStringCentered("GAME OVER", width / 2, height / 2);
    }   

    private void drawBackground(Window window) {
        window.setColor(Colors.BACKGROUND.getColor());
        window.fillRect(0, 0, width, height);
    }
}
