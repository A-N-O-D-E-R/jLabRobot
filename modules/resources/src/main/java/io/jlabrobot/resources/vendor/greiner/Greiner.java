package io.jlabrobot.resources.vendor.greiner;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;
import java.io.IOException;

/**
 * Factory for creating Greiner brand microplate instances.
 *
 * Greiner Bio-One is a manufacturer of premium laboratory plasticware and consumables,
 * offering innovative microplate solutions for research, diagnostics, and industrial applications.
 * This class provides factory methods to create commonly used Greiner plate models
 * with their standardized specifications.
 */
public class Greiner {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("greiner");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Greiner catalog", e);
        }
    }

    /**
     * Creates a Greiner CELLSTAR 96-well plate.
     *
     * Standard 96-well plate (art. no. 655101) with CELLSTAR surface for optimal cell attachment
     * and growth in cell culture applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Greiner CELLSTAR 96-well plate
     */
    public static Plate cellstar96(String name) {
        return catalog.createPlate("greiner", "655101", name);
    }

    /**
     * Creates a Greiner microplate 384-well plate.
     *
     * High-density 384-well plate (art. no. 651201) for increased throughput screening applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Greiner microplate 384-well plate
     */
    public static Plate microplate384(String name) {
        return catalog.createPlate("greiner", "651201", name);
    }

    /**
     * Creates a Greiner MASTERBLOCK 96-well deep well plate.
     *
     * 96-well deep well plate (art. no. 780201) with 2000 µL well capacity for bulk storage
     * and handling in automated liquid handling workflows.
     *
     * @param name the unique instance name for the plate
     * @return a new Greiner MASTERBLOCK 96-well plate
     */
    public static Plate masterblock96(String name) {
        return catalog.createPlate("greiner", "780201", name);
    }
}
