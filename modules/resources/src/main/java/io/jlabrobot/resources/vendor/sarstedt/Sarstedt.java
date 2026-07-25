package io.jlabrobot.resources.vendor.sarstedt;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

/**
 * Factory for creating Sarstedt brand microplate instances.
 *
 * Sarstedt is a leading supplier of laboratory consumables and offers various microplate
 * formats for life sciences applications. This class provides factory methods to create
 * commonly used Sarstedt plate models with their standardized specifications.
 */
public class Sarstedt {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("sarstedt");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Sarstedt catalog", e);
        }
    }

    /**
     * Creates a Sarstedt 82.1582.001 96-well plate.
     *
     * Standard 96-well plate format (art. no. 82.1582.001).
     *
     * @param name the unique instance name for the plate
     * @return a new Sarstedt 82.1582.001 plate
     */
    public static Plate plate821582001(String name) {
        return catalog.createPlate("sarstedt", "82.1582.001", name);
    }

    /**
     * Creates a Sarstedt 82.1583.001 96-well plate.
     *
     * Standard 96-well plate format (art. no. 82.1583.001).
     *
     * @param name the unique instance name for the plate
     * @return a new Sarstedt 82.1583.001 plate
     */
    public static Plate plate821583001(String name) {
        return catalog.createPlate("sarstedt", "82.1583.001", name);
    }

    /**
     * Creates a Sarstedt 82.1920.500 deep well plate.
     *
     * 96-well deep well plate format (art. no. 82.1920.500) for large volume applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Sarstedt deep well plate
     */
    public static Plate deepWell821920500(String name) {
        return catalog.createPlate("sarstedt", "82.1920.500", name);
    }
}
