package model;

import abstraction.Plant;
import settings.Settings;

public class Grass extends Plant {
    private Cell cell; // Клетка, в которой находится трава

    // Устанавливаем клетку для травы
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
    public void reproduction() {
        // Трава размножается сама, без вмешательства
    }

    // Процесс роста травы
    @Override
    public void growth() {
        Grass offspring = new Grass(); // Создаём новую траву
        if (this.cell != null) {
            offspring.setCell(this.cell); // Привязываем к клетке
            cell.addOrganism(offspring); // Добавляем в клетку
        }
    }

    // Метод для создания новой травы через поток
    @Override
    public void run() {
        while (isAlive) {
            try {
                Thread.sleep(Settings.GRASS_GROWTH_RATE_MS); // Задержка на рост
                Grass newGrass = new Grass();
                if (cell != null) {
                    cell.addOrganism(newGrass); // Добавляем новую траву в клетку
                    newGrass.setCell(cell);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}