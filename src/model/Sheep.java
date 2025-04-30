package model;

import abstraction.Animal;
import abstraction.Organism;
import settings.OrganismType;
import settings.Settings;

import java.util.List;
import java.util.Random;

public class Sheep extends Animal {
    private final OrganismType type;

    // Конструктор овцы
    public Sheep() {
        this.type = OrganismType.SHEEP;
        this.energy = type.getMaxEnergy();
    }

    @Override
    public void reproduction() {
        // Ничего не делаем, потому что размножение  через reproduction(List<Animal> newAnimals)
    }

    @Override
    public void reproduction(List<Animal> newAnimals) {
        // Размножение овец
        if (this.energy >= type.getMaxEnergy() * 0.1) {
            Sheep offspring = new Sheep();
            offspring.energy = type.getMaxEnergy() / 2;
            this.energy /= 2;
            offspring.setCell(this.cell); // <-- Важно: установить клетку
            newAnimals.add(offspring);
        }
    }

    @Override
    public void eat(List<Animal> animalsAround) {
        if (cell == null) return;

        // Смотрим, есть ли трава в клетке
        List<Organism> organisms = cell.getOrganisms();
        for (Organism org : organisms) {
            if (org instanceof Grass && org.isAlive()) {
                org.die(); // Трава умирает
                this.energy += 20; // Овца получает энергию
                break; // Перестаём искать после первого поедания травы
            }
        }
    }

    @Override
    public void move() {
        if (cell == null) return;

        // Случайное движение овцы по клеткам
        int dx = randomMove();
        int dy = randomMove();
        int newX = cell.getX() + dx;
        int newY = cell.getY() + dy;

        Island island = cell.getIsland();
        if (island != null && island.isValidPosition(newX, newY)) {
            cell.removeOrganism(this); // Убираем овцу из старой клетки
            Cell newCell = island.getCell(newX, newY);
            newCell.addOrganism(this); // Добавляем овцу в новую клетку
            this.cell = newCell;
        }
        this.energy -= 10; // Потери энергии от движения
        if (this.energy <= 0) {
            die(); // Овца умирает, если энергия заканчивается
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