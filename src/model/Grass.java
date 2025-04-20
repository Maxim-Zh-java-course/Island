package model;

import abstraction.Plant;

public class Grass extends Plant {

    @Override
    public void growth() {
        System.out.println("Grass is growing");
    }

    @Override
    public void reproduction() {
        System.out.println("Grass is reproduction");
    }
}
