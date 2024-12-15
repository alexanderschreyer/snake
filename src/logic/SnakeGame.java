package src.logic;

import java.util.ArrayList;

import gui.Window;
import src.gameobjects.Player;
import src.enums.Colors;
import src.enums.Direction;
import src.gameobjects.Apple;
import src.gameobjects.Obstacle;

public class SnakeGame {
    private int width;
    private int height;
    private Grid grid;
    private Player player;
    private Apple apple;
    private ArrayList<Apple> eatenApples;
    private int steps;

    private ArrayList<Obstacle> obstacles;
    
    private boolean justEaten;
    private int countOne;
    private int countTwo;

    private boolean pause = false;
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

    public boolean getPause() {
        return pause;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    private void initializeGameState() {
        player = new Player(grid);
        apple = new Apple(grid);
        eatenApples = new ArrayList<Apple>();
        /*
        obstacles = new ArrayList<Obstacle>();
        for (int i = 1; i <= 6; i++) {
            obstacles.add(new Obstacle(grid));
        }
         */
        steps = 0;
        countOne = 0;
        countTwo = 0;
        gameOver = false;
    }

    public void handleEvents(Window window) {
        handleInput(window);
        if (!pause && !gameOver) {
            step();
        }
    }

    public void handleInput(Window window) {
        // Player movement
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
        // Menu interaction
        if (window.isKeyPressed("escape") && !pause && !gameOver) {
            pause = true;
        }
        if (window.isKeyPressed("space") && pause) {
            pause = false;
        }
        if (window.isKeyPressed("x") && (pause || gameOver)) {
            System.exit(0);
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
                player.addAppendices(eatenApples);
                eatenApples.removeAll(eatenApples);
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
            player.setScore(player.getScore() + apple.getValue());
            eatenApples.add(apple);
            apple = new Apple(grid);
            countOne += 1;
            justEaten = true;
        }
    }

    private void checkCollision() {
        String playerLoc = (player.getLocation()).getTileName();
        for (Tile appendix : player.getAppendices()) {
            if (appendix.getTileName().equals(playerLoc)) {
                gameOver = true;
            }
        }
    }

    public void drawGame(Window window) {
        drawBackground(window);
        // drawTiles(window);
        /*
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(window);
        }
        */
        apple.draw(window);
        player.draw(window);
        if (pause) {
            drawPause(window);
        }
        if (gameOver) {
            drawGameOver(window);
        }
    }

    private void drawPause(Window window) {
        drawBackground(window);
        player.drawScore(window);
        window.setColor(Colors.FONT.getColor());
        window.setFontSize(50);
        window.drawStringCentered("PAUSE", width / 2, height / 2);
        window.setFontSize(20);
        window.drawStringCentered("Press space to continue, press x to end the game", width / 2, height / 3 * 1.7);
    }

    private void drawGameOver(Window window) {
        drawBackground(window);
        player.drawScore(window);
        window.setColor(Colors.FONT.getColor());
        window.setFontSize(50);
        window.drawStringCentered("GAME OVER", width / 2, height / 2);
        window.setFontSize(20);
        window.drawStringCentered("Press r to restart, press x to end the game", width / 2, height / 3 * 1.7);
        
    }   

    private void drawBackground(Window window) {
        window.setColor(Colors.BACKGROUND.getColor());
        window.fillRect(0, 0, width, height);
    }

    /*
     * A method for checking whether the tiles
     * of the grid are placed correctly.
     */
    public void drawTiles(Window window) {
        window.setColor(200,200,200);
        Tile[] tiles = grid.getTiles();
        for (int i = 0; i < tiles.length; i += 2) {
            Tile tile = tiles[i];
            window.fillRect(tile.getX(), tile.getY(), tile.getTileWidth(), tile.getTileHeight());
        }
    }
}
