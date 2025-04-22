package factory;

import model.Wolf;
import model.Sheep;
import model.Grass;
import settings.OrganismType;
import abstraction.Organism;

public class OrganismFactory {

    public static Organism createOrganism(OrganismType type) {
        return switch (type) {
            case WOLF -> new Wolf();
            case SHEEP -> new Sheep();
            case GRASS -> new Grass();
        };
    }
}
