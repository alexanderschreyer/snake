package src.enums;

import gui.Color;

public enum Colors {
    APPLE(141, 11, 65),
    BACKGROUND(232, 236, 215),
    FONT(211, 157, 85),
    SNAKE(71, 102, 59);

    private Color color;

    Colors(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public Color getColor() {
        return color;
    }
}
