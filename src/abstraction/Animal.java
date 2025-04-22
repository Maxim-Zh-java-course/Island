package abstraction;

public abstract class Animal extends Organism {

    protected int x, y;             // Координаты на острове
    protected int energy = 20;      // Начальная энергия

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getEnergy() {
        return energy;
    }

    public void decreaseEnergy() {
        energy -= 3; // Например, каждый шаг тратит 3 энергии
    }

    public void increaseEnergy(int amount) {
        energy = Math.min(energy + amount, 100); // максимум 100
    }

    public abstract void move();

    public abstract void eat();

    public abstract void reproduction();
}
