package model;

import abstraction.Organism;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cell {
    private final List<Organism> organisms = new CopyOnWriteArrayList<>();

    public void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public void removeOrganism(Organism organism) {
        organisms.remove(organism);
    }

}
