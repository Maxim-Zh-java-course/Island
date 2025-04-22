package simulation;

import abstraction.Animal;
import model.Island;
import abstraction.Organism;
import model.Cell;
import model.Wolf;
import model.Sheep;
import model.Grass;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class SimulationManager {

    private final Island island;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private int tick = 0;

    // счётчики
    private int bornWolves = 0, bornSheep = 0, bornGrass = 0;
    private int eatenSheep = 0, eatenGrass = 0;
    private int deadWolves = 0, deadSheep = 0;

    public SimulationManager(Island island) {
        this.island = island;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::nextTick, 0, 1, TimeUnit.SECONDS);
    }

    private void nextTick() {
        tick++;
        System.out.println("======= Tick #" + tick + " =======");

        int wolves = 0, sheep = 0, grass = 0;

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Cell cell = island.getCell(x, y);
                List<Organism> organisms = new ArrayList<>(cell.getOrganisms());

                for (Organism organism : organisms) {
                    if (organism instanceof Animal animal) {
                        moveAnimal(animal, x, y);

                        Cell currentCell = island.getCell(animal.getX(), animal.getY());

                        // 🦴 Пытаемся поесть
                        boolean ate = tryToEat(animal, currentCell);

                        // 🔋 Если не поел – теряем энергию
                        if (!ate) {
                            animal.decreaseEnergy();
                        }

                        // ☠️ Проверка на смерть
                        if (animal.getEnergy() <= 0) {
                            currentCell.removeOrganism(animal);
                            if (animal instanceof Wolf) deadWolves++;
                            else if (animal instanceof Sheep) deadSheep++;
                        }
                    }
                }
            }
        }

        // Пересчёт выживших
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                for (Organism org : island.getCell(x, y).getOrganisms()) {
                    if (org instanceof Wolf) wolves++;
                    else if (org instanceof Sheep) sheep++;
                    else if (org instanceof Grass) grass++;
                }
            }
        }

        growGrass();
        reproduceAnimals();

        printStats(wolves, sheep, grass);

    }



    private void printStats(int wolves, int sheep, int grass) {
        System.out.println("🐺 Волков: " + wolves + " (родилось: " + bornWolves + ", умерло: " + deadWolves + ")");
        System.out.println("🐑 Овец:   " + sheep + " (родилось: " + bornSheep + ", съедено: " + eatenSheep + ", умерло: " + deadSheep + ")");
        System.out.println("🌿 Травы:  " + grass + " (выросло: " + bornGrass + ", съедено: " + eatenGrass + ")");
        System.out.println();
    }

    private final Random random = new Random();

    private void moveAnimal(Animal animal, int oldX, int oldY) {
        int width = island.getWidth();
        int height = island.getHeight();

        // Убираем из старой клетки
        island.getCell(oldX, oldY).removeOrganism(animal);

        // Двигаемся в случайную сторону
        int dx = 0, dy = 0;
        switch (random.nextInt(4)) {
            case 0 -> dx = -1; // влево
            case 1 -> dx = 1;  // вправо
            case 2 -> dy = -1; // вверх
            case 3 -> dy = 1;  // вниз
        }

        int newX = Math.max(0, Math.min(width - 1, oldX + dx));
        int newY = Math.max(0, Math.min(height - 1, oldY + dy));

        island.getCell(newX, newY).addOrganism(animal);
        animal.move(); // лог для наглядности
    }

    private boolean tryToEat(Animal animal, Cell cell) {
        List<Organism> foodCandidates = new ArrayList<>(cell.getOrganisms());

        for (Organism target : foodCandidates) {
            // Волк ест овцу
            if (animal instanceof Wolf && target instanceof Sheep) {
                cell.removeOrganism(target);
                animal.increaseEnergy(10);
                eatenSheep++;
                return true;
            }

            // Овца ест траву
            if (animal instanceof Sheep && target instanceof Grass) {
                cell.removeOrganism(target);
                animal.increaseEnergy(5);
                eatenGrass++;
                return true;
            }
        }

        return false;
    }

    private void growGrass() {
        int totalCells = island.getWidth() * island.getHeight();
        int grassToGrow = totalCells / 10; // например, 10% клеток в такт

        Random random = new Random();

        for (int i = 0; i < grassToGrow; i++) {
            int x = random.nextInt(island.getWidth());
            int y = random.nextInt(island.getHeight());
            Cell cell = island.getCell(x, y);

            // Только если в клетке ещё нет травы
            boolean hasGrass = cell.getOrganisms().stream()
                    .anyMatch(o -> o instanceof Grass);

            if (!hasGrass) {
                Grass grass = new Grass();
                grass.setPosition(x, y);
                cell.addOrganism(grass);
                bornGrass++;
            }
        }
    }
    private void reproduceAnimals() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Cell cell = island.getCell(x, y);
                List<Organism> organisms = new ArrayList<>(cell.getOrganisms());

                long wolves = organisms.stream().filter(o -> o instanceof Wolf).count();
                long sheep = organisms.stream().filter(o -> o instanceof Sheep).count();

                if (wolves >= 2) {
                    Wolf babyWolf = new Wolf();
                    babyWolf.setPosition(x, y);
                    cell.addOrganism(babyWolf);
                    bornWolves++;
                }

                if (sheep >= 2) {
                    Sheep babySheep = new Sheep();
                    babySheep.setPosition(x, y);
                    cell.addOrganism(babySheep);
                    bornSheep++;
                }
            }
        }
    }


}
