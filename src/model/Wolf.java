package model;

import abstraction.Animal;

public class Wolf extends Animal {



    @Override
    public void reproduction() {
        System.out.println("Wolf is reproduction");
    }

    @Override
    public void eat() {
        System.out.println("Wolf is eating");
    }

    @Override
    public void move() {
        System.out.println("Wolf is moving");
    }
}
