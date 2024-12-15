package src.logic;

import java.util.ArrayList;

import gui.Window;
import src.gameobjects.Player;
import src.enums.Colors;
import src.enums.Direction;
import src.gameobjects.Food;
import src.gameobjects.Apple;
import src.logic.Randomizer;

public class SnakeGame {
    private int width;
    private int height;
    private Grid grid;
    private Player player;
    private Food food;
    private Apple apple;
    private ArrayList<Food> foodEaten;
    private boolean justEaten;

    private int stepCount;
    private int stepCountSinceEaten;
    private int foodCount;
    private int foodCountPipeline;

    private boolean start;
    private boolean pause;
    private boolean gameOver;

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
        spawnFood();
        apple = null;
        foodEaten = new ArrayList<Food>();
        stepCount = 0;
        foodCountPipeline = 0;
        stepCountSinceEaten = 0;
        foodCount = 1;
        start = true;
        pause = false;
        gameOver = false;
    }

    public void handleEvents(Window window) {
        handleMenuControls(window);
        if (!pause && !gameOver && !start) {
            handlePlayerControls(window);
            step();
        }
    }

    public void handlePlayerControls(Window window) {
        if (window.isKeyPressed("w") && player.getDirection() != Direction.SOUTH && player.hasMoved()) {
            player.setDirection(Direction.NORTH);
            player.setHasMoved(false);
        } else if (window.isKeyPressed("a") && player.getDirection() != Direction.EAST && player.hasMoved()) {
            player.setDirection(Direction.WEST);
            player.setHasMoved(false);
        } else if (window.isKeyPressed("s") && player.getDirection() != Direction.NORTH && player.hasMoved()) {
            player.setDirection(Direction.SOUTH);
            player.setHasMoved(false);
        } else if (window.isKeyPressed("d") && player.getDirection() != Direction.WEST && player.hasMoved()) {
            player.setDirection(Direction.EAST);
            player.setHasMoved(false);
        }
    }

    private void handleMenuControls(Window window) {
        if (window.isKeyPressed("escape") && !pause && !gameOver && !start) {
            pause = true;
        }
        if (window.isKeyPressed("space") && (pause || start)) {
            pause = false;
            start = false;
        }
        if (window.isKeyPressed("x") && (pause || gameOver || start)) {
            System.exit(0);
        }
        if (window.isKeyPressed("r")  && gameOver) {
            initializeGameState();
        }
    }

    public void step() {
        stepCount += 1;
        if (stepCount % 10 == 0) {
            player.saveLastTile();
            player.move(grid);
            player.setHasMoved(true);
            player.adjustAppendices();
            if (justEaten) {
                stepCountSinceEaten += 1;
            }
            if (justEaten && foodCountPipeline == stepCountSinceEaten) {
                player.addAppendices(foodEaten);
                foodEaten.removeAll(foodEaten);
                justEaten = false;
                foodCountPipeline = 0;
                stepCountSinceEaten = 0;
            }
            checkFood();
            checkApple();
            checkCollision();
        }
    }

    private void spawnFood() {
        Tile randTile = Randomizer.randomizedLocation(grid, player);
        food = new Food(randTile);
    }

    private void spawnApple() {
        Tile randTile = Randomizer.randomizedLocation(grid, player);
        apple = new Apple(randTile);
    }

    private void checkFood() {
        String playerLoc = (player.getLocation()).getTileName();
        String foodLoc = (food.getLocation()).getTileName();
        if (playerLoc.equals(foodLoc)) {
            player.setScore(player.getScore() + food.getValue());
            foodEaten.add(food);
            spawnFood();
            foodCount += 1;
            if (foodCount % 10 == 0) {
                spawnApple();
            }
            foodCountPipeline += 1;
            justEaten = true;
        }
    }

    private void checkApple() {
        if (apple != null) {
            if (stepCount % 200 == 0) {
                apple.decreaseValue();
                if (apple.getValue() == 0) {
                    apple = null;
                } 
            }
        }
        if (apple != null) {
            String playerLoc = (player.getLocation()).getTileName();
            String appleLoc = (apple.getLocation()).getTileName();
            if (playerLoc.equals(appleLoc)) {
                player.setScore(player.getScore() + apple.getValue());
                foodEaten.add(apple);
                apple = null;
                foodCountPipeline += 1;
                justEaten = true;
            }
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
        food.draw(window);
        if (apple != null) {
            apple.draw(window);
        }
        player.draw(window);
        if (start) {
            drawStart(window);
        }
        if (pause) {
            drawPause(window);
        }
        if (gameOver) {
            drawGameOver(window);
        }
    }

    private void drawStart(Window window) {
        drawBackground(window);
        window.setColor(Colors.FONT_2.getColor());
        window.setFontSize(50);
        window.drawStringCentered("SNAKE", width / 2, height / 2);
        window.setColor(Colors.FONT_1.getColor());
        window.setFontSize(20);
        window.drawStringCentered("Press SPACE to start, press X to end the game", width / 2, height / 3 * 1.8);
        window.setBold(false);
        window.setFontSize(10);
        window.drawStringCentered("A. SCHREYER, 2024", width / 2, 25);
    }

    private void drawPause(Window window) {
        drawBackground(window);
        player.drawScore(window);
        window.setColor(Colors.FONT_2.getColor());
        window.setFontSize(50);
        window.drawStringCentered("PAUSE", width / 2, height / 2);
        window.setColor(Colors.FONT_1.getColor());
        window.setFontSize(20);
        window.drawStringCentered("Press SPACE to continue, press X to end the game", width / 2, height / 3 * 1.8);
    }

    private void drawGameOver(Window window) {
        drawBackground(window);
        player.drawScore(window);
        window.setColor(Colors.FONT_2.getColor());
        window.setFontSize(50);
        window.drawStringCentered("GAME OVER", width / 2, height / 2);
        window.setColor(Colors.FONT_1.getColor());
        window.setFontSize(20);
        window.drawStringCentered("Press R to restart, press X to end the game", width / 2, height / 3 * 1.8);
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
