package abstraction;

import model.Cell;
import java.util.List;

public abstract class Animal extends Organism {
    protected int energy; // Энергия животного
    protected Cell cell; // Клетка, в которой находится животное

    // Устанавливаем клетку для животного
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    // Получаем текущую энергию животного
    public int getEnergy() {
        return energy;
    }

    @Override
    public void die() {
        super.die(); // Умираем
        if (cell != null) {
            cell.removeOrganism(this); // Убираем из клетки
        }
    }

    // Абстрактные методы для всех животных
    public abstract void reproduction(List<Animal> newAnimals); // Размножение
    public abstract void eat(List<Animal> animalsAround); // Поедание пищи
    public abstract void move(); // Движение
}