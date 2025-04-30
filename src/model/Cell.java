package model;

import abstraction.Animal;
import abstraction.Organism;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final int x;
    private final int y;
    private final Island island;
    private final List<Organism> organisms = new ArrayList<>(); //Список организмов в клетке

    // Конструктор клетки
    public Cell(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.island = island;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Island getIsland() {
        return island;
    }

    // Добавляем организм в клетку
    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    // Убираем организм из клетки
    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

    // Получаем все организмы в клетке
    public List<Organism> getOrganisms() {
        return organisms;
    }

    // Получаем только животных из клетки
    public List<Animal> getAnimals() {
        List<Animal> animals = new ArrayList<>();
        for (Organism org : organisms) {
            if (org instanceof Animal) {
                animals.add((Animal) org);
            }
        }
        return animals;
    }
}