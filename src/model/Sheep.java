package model;

import abstraction.Animal;
import settings.OrganismType;

public class Sheep extends Animal {
    private final OrganismType type;

    public Sheep() {
        this.type = OrganismType.SHEEP;
        this.energy = type.getMaxEnergy();
    }

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
