package settings;

public enum OrganismType {
    WOLF(50, 8,3),
    SHEEP(70, 15,3),
    GRASS(10, 0,0);

    private final int maxEnergy;
    private final int notHungry;
    private final int speed;

    OrganismType(int maxEnergy, int notHungry,int speed) {
        this.maxEnergy = maxEnergy;
        this.notHungry = notHungry;
        this.speed = speed;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getSpeed() {
        return speed;
    }

    public int getNotHungry() {
        return notHungry;
    }
}
