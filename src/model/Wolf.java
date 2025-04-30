package model;

import abstraction.Animal;
import settings.OrganismType;
import settings.Settings;

import java.util.List;
import java.util.Random;

public class Wolf extends Animal {
    private final OrganismType type;

    public Wolf() {
        this.type = OrganismType.WOLF;
        this.energy = type.getMaxEnergy();
    }

    public void reproduction() {
        // Ничего не делаем, потому что размножение у волков через reproduction(List<Animal> newAnimals)
    }

    @Override
    public void reproduction(List<Animal> newAnimals) {
        if (this.energy >= type.getMaxEnergy() * 0.9) {
            Wolf offspring = new Wolf();
            offspring.energy = type.getMaxEnergy() / 2;
            this.energy /= 2;
            offspring.setCell(this.cell);
            newAnimals.add(offspring);
        }
    }

    @Override
    public void eat(List<Animal> animalsAround) {
        for (Animal animal : animalsAround) {
            if (animal instanceof Sheep && animal.isAlive()) {
                animal.die(); // Волк съедает овцу
                this.energy += 30; // Волк получает больше энергии от овцы
                break;
            }
        }
    }

    @Override
    public void move() {
        if (cell == null) return;

        int dx = randomMove();
        int dy = randomMove();
        int newX = cell.getX() + dx;
        int newY = cell.getY() + dy;

        Island island = cell.getIsland();
        if (island != null && island.isValidPosition(newX, newY)) {
            cell.removeOrganism(this); // Убираем волка из старой клетки
            Cell newCell = island.getCell(newX, newY);
            newCell.addOrganism(this); // Добавляем волка в новую клетку
            this.cell = newCell;
        }
        this.energy -= 10; // Потери энергии от движения
        if (this.energy <= 0) {
            die(); // Волк умирает, если энергия заканчивается
        }
    }

    private int randomMove() {
        int[] moves = {-1, 0, 1};
        return moves[new Random().nextInt(moves.length)];
    }

    @Override
    public void run() {
        while (isAlive) {
            move();
            try {
                Thread.sleep(Settings.TICK_DURATION_MS); //Задержка для такта
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}