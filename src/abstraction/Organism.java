package abstraction;

public abstract class Organism implements Runnable {
    protected boolean isAlive = true; // Жив ли организм

    // Умираем
    public void die() {
        isAlive = false;
    }

    // Проверяем, жив ли организм
    public boolean isAlive() {
        return isAlive;
    }

    // Абстрактный метод для размножения
    public abstract void reproduction();
}