package snakegame.model;

import java.awt.*;

public class ColorPoint {
    private int x, y;
    private Color color;

    public ColorPoint(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}