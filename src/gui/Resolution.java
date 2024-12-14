package src.gui;

public enum Resolution {
    // Square-shaped aspect ratios
    RES_400(400),
    RES_600(600),
    RES_800(800),
    RES_1024(1024),

    // Widescreen aspect ratios
    RES_HD(1280, 720),
    RES_FHD(1920, 1280);

    private int width;
    private int height;

    Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    Resolution(int side) {
        this(side, side); 
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
