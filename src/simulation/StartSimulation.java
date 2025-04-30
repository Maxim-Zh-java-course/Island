package simulation;

import abstraction.Animal;
import abstraction.Plant;
import model.*;
import settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class StartSimulation {
    private Island island;

    private final List<Animal> animals = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private volatile boolean running = true; // Флаг для остановки

    public void initialize() {
        island = new Island(20, 20); // Создаем остров размером 10x10

        // Создаем овец
        for (int i = 0; i < 20; i++) {
            Sheep sheep = new Sheep();
            Cell randomCell = getRandomCell();
            sheep.setCell(randomCell);
            randomCell.addOrganism(sheep);
            animals.add(sheep);
        }

        // Создаем волков
        for (int i = 0; i < 5; i++) {
            Wolf wolf = new Wolf();
            Cell randomCell = getRandomCell();
            wolf.setCell(randomCell);
            randomCell.addOrganism(wolf);
            animals.add(wolf);
        }

        // Создаем траву
        for (int i = 0; i < 20; i++) {
            Grass grass = new Grass();
            Cell randomCell = getRandomCell();
            grass.setCell(randomCell);
            randomCell.addOrganism(grass);
            plants.add(grass);
        }
    }

    // Запуск симуляции
    public void start() {
        for (Animal animal : animals) {
            new Thread(animal).start(); // Стартуем каждый поток для животного
        }
        for (Plant plant : plants) {
            new Thread(plant).start(); // Стартуем каждый поток для растения
        }
        new Thread(this::simulateLifeCycle).start(); // Симуляция жизненного цикла
    }

    // Симуляция жизненного цикла
    private void simulateLifeCycle() {
        while (running) {
            List<Animal> newAnimals = new ArrayList<>();
            List<Animal> aliveAnimals = new ArrayList<>(animals);

            // Для каждого животного выполняем действия: поедание, размножение
            for (Animal animal : aliveAnimals) {
                if (animal.isAlive()) {
                    animal.eat(aliveAnimals); // Овцы едят траву
                    animal.reproduction(newAnimals); // Размножение
                }
            }

            // Добавляем новых животных и удаляем мертвых
            animals.addAll(newAnimals);
            animals.removeIf(a -> !a.isAlive());
            plants.removeIf(p -> !p.isAlive());

            // Выводим статистику
            printStatistics();

            // Проверяем, есть ли хотя бы один вид
            long wolves = animals.stream().filter(a -> a instanceof Wolf).count();
            long sheep = animals.stream().filter(a -> a instanceof Sheep).count();
            long grass = plants.stream().filter(p -> p instanceof Grass).count();

            if (wolves == 0 || sheep == 0 || grass == 0) {
                System.out.println("❗ Симуляция окончена! Один из видов исчез.");
                running = false; // Останавливаем симуляцию
                break;
            }

            try {
                Thread.sleep(Settings.TICK_DURATION_MS); // Задержка на такт
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void printStatistics() {
        long wolves = animals.stream().filter(a -> a instanceof Wolf).count();
        long sheep = animals.stream().filter(a -> a instanceof Sheep).count();
        long grass = plants.stream().filter(p -> p instanceof Grass).count();
        System.out.printf("Волков: %d, Овец: %d, Травы: %d%n", wolves, sheep, grass);
    }

    private Cell getRandomCell() {
        int x = (int) (Math.random() * island.getWidth());
        int y = (int) (Math.random() * island.getHeight());
        return island.getCell(x, y);
    }

    // Останавливаем симуляцию
    public void stop() {
        running = false;
    }
}