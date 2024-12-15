package src.enums;

import gui.Color;

public enum Colors {
    FOOD(231, 41, 41),
    BACKGROUND(229, 225, 218),
    BACKGROUND_CACTUS(209, 205, 208),
    FONT_1(138, 131, 110),
    FONT_2(102, 97, 82),
    SNAKE_HEAD(75, 89, 69),
    SNAKE_BODY(71, 102, 59),
    SNAKE_TAIL(242, 159, 88),
    SNAKE_EYES(250, 250, 250);

    private Color color;

    Colors(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public Color getColor() {
        return color;
    }
}
