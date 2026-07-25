package io.jlabrobot.resources.vendor.corning;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;
import java.io.IOException;

/**
 * Factory for creating Corning brand microplate instances.
 *
 * Corning is a prominent manufacturer of laboratory glassware and plasticware,
 * offering a comprehensive range of microplate formats for research and clinical applications.
 * This class provides factory methods to create commonly used Corning plate models
 * with their standardized specifications.
 */
public class Corning {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("corning");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Corning catalog", e);
        }
    }

    /**
     * Creates a Corning 3596 96-well plate.
     *
     * Standard 96-well plate (cat. no. 3596) for general laboratory use.
     *
     * @param name the unique instance name for the plate
     * @return a new Corning 3596 plate
     */
    public static Plate plate3596(String name) {
        return catalog.createPlate("corning", "3596", name);
    }

    /**
     * Creates a Corning 3370 96-well plate.
     *
     * 96-well plate (cat. no. 3370) optimized for cell culture and proliferation assays.
     *
     * @param name the unique instance name for the plate
     * @return a new Corning 3370 plate
     */
    public static Plate plate3370(String name) {
        return catalog.createPlate("corning", "3370", name);
    }

    /**
     * Creates a Corning 3704 384-well plate.
     *
     * High-density 384-well plate (cat. no. 3704) for increased throughput screening applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Corning 3704 plate
     */
    public static Plate plate3704(String name) {
        return catalog.createPlate("corning", "3704", name);
    }

    /**
     * Creates a Corning 3912 384-well plate.
     *
     * Ultra-low attachment 384-well plate (cat. no. 3912) for cell suspension and spheroid cultures.
     *
     * @param name the unique instance name for the plate
     * @return a new Corning 3912 plate
     */
    public static Plate plate3912(String name) {
        return catalog.createPlate("corning", "3912", name);
    }
}
