package src.gui;

import gui.Window;
import src.logic.SnakeGame;
import src.logic.Controls;

public class Main {
    private static final Resolution RES = Resolution.RES_400;
    private static final int WIDTH = RES.getWidth();
    private static final int HEIGHT = RES.getHeight();
    private static final String NAME = "Snake";

    public static void main(String[] args) {
        Window gui = new Window(NAME, WIDTH, HEIGHT);
        SnakeGame game = new SnakeGame(WIDTH, HEIGHT);

        gui.open();

        while (gui.isOpen()) {

            Controls.handleEvents(gui);

            game.drawTiles(gui);

            gui.refreshAndClear(10);
        }

    }
}
