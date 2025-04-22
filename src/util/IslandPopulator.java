package util;

import factory.OrganismFactory;
import model.Island;
import settings.OrganismType;
import abstraction.Organism;

import java.util.Random;

public class IslandPopulator {

    private static final Random random = new Random();

    public static void populate(Island island, int wolfCount, int sheepCount, int grassCount) {
        populateOrganism(island, OrganismType.WOLF, wolfCount);
        populateOrganism(island, OrganismType.SHEEP, sheepCount);
        populateOrganism(island, OrganismType.GRASS, grassCount);
    }

    private static void populateOrganism(Island island, OrganismType type, int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(island.getWidth());
            int y = random.nextInt(island.getHeight());

            Organism organism = OrganismFactory.createOrganism(type);
            island.getCell(x, y).addOrganism(organism);
        }
    }
}
