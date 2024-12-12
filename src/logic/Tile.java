package src.logic;

public class Tile {
    private int colNum;
    private int rowNum;
    private int tileWidth;
    private int tileHeight;
    private int x;
    private int y;

    Tile(int colNum, int rowNum, int tileWidth, int tileHeight, int x, int y) {
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.x = x;
        this.y = y;
    }

    Tile(int colNum, int rowNum, int tileSide, int x, int y) {
        this(colNum, rowNum, tileSide, tileSide, x, y);
    }

    public int getColNum() {
        return colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTileName() {
        return colNum + "-" + rowNum;
    }
}
