package model;

import abstraction.Animal;
import settings.OrganismType;

public class Wolf extends Animal {
    private final OrganismType type;

    public Wolf() {
        this.type = OrganismType.WOLF;
        this.energy = type.getMaxEnergy(); // начинаем с полной энергии
    }


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
