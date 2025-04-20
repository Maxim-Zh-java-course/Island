package model;

import abstraction.Animal;

public class Sheep extends Animal {


    @Override
    public void move() {
        System.out.println("Sheep is moving");

    }

    @Override
    public void eat() {
        System.out.println("Sheep is eating");
    }

    @Override
    public void reproduction() {
        System.out.println("Sheep is reproduction");
    }
}
