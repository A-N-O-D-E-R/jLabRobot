package io.jlabrobot.resources.vendor.falcon;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

/**
 * Factory for creating Falcon brand microplate instances.
 *
 * Falcon (a Corning brand) provides a wide range of microplate formats for research
 * and diagnostic applications. This class provides convenient factory methods to
 * instantiate commonly used Falcon plate models with their standard specifications.
 */
public class Falcon {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("falcon");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Falcon catalog", e);
        }
    }

    /**
     * Creates a Falcon 353072 96-well plate.
     *
     * Standard 96-well plate (cat. no. 353072) for general-purpose applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Falcon 353072 plate
     */
    public static Plate plate353072(String name) {
        return catalog.createPlate("falcon", "353072", name);
    }

    /**
     * Creates a Falcon 353916 96-well plate.
     *
     * 96-well plate (cat. no. 353916) optimized for cell culture applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Falcon 353916 plate
     */
    public static Plate plate353916(String name) {
        return catalog.createPlate("falcon", "353916", name);
    }

    /**
     * Creates a Falcon 353293 96-well plate.
     *
     * 96-well plate (cat. no. 353293) for immunoassay and ELISA applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Falcon 353293 plate
     */
    public static Plate plate353293(String name) {
        return catalog.createPlate("falcon", "353293", name);
    }

    /**
     * Creates a Falcon 353263 96-well plate.
     *
     * 96-well deep well plate (cat. no. 353263) with increased well volume for bulk applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Falcon 353263 plate
     */
    public static Plate plate353263(String name) {
        return catalog.createPlate("falcon", "353263", name);
    }
}
