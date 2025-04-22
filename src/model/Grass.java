package model;

import abstraction.Plant;

public class Grass extends Plant {
    protected int x, y;

    @Override
    public void growth() {
        System.out.println("Grass is growing");
    }

    @Override
    public void reproduction() {
        System.out.println("Grass is reproduction");
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
