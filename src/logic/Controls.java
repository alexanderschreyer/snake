package src.logic;

import gui.Window;

public class Controls {
    
    public static void handleEvents(Window window) {
        if (window.isKeyPressed("escape")) {
            window.close();
        }
    }
}
