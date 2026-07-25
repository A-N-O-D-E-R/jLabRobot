package io.jlabrobot.resources.vendor.axygen;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

/**
 * Factory for creating Axygen brand microplate instances.
 *
 * Axygen supplies a variety of microplate formats for different applications
 * including PCR, deep well, and standard 96/384 well plates. This class provides
 * convenient factory methods to instantiate commonly used Axygen plate models
 * with their standardized specifications.
 */
public class Axygen {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("axygen");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Axygen catalog", e);
        }
    }

    /**
     * Creates an Axygen P-96-450V-C 96-well plate.
     *
     * Standard 96-well plate format with 450 µL well capacity.
     *
     * @param name the unique instance name for the plate
     * @return a new Axygen P-96-450V-C plate
     */
    public static Plate p96450VC(String name) {
        return catalog.createPlate("axygen", "P-96-450V-C", name);
    }

    /**
     * Creates an Axygen P-DW-2ML deep well plate.
     *
     * 96-well deep well plate format with 2 mL well capacity for larger volume applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Axygen deep well plate
     */
    public static Plate deepWell2mL(String name) {
        return catalog.createPlate("axygen", "P-DW-2ML", name);
    }

    /**
     * Creates an Axygen PCR-96-C PCR plate.
     *
     * 96-well PCR plate format optimized for polymerase chain reaction applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Axygen PCR-96-C plate
     */
    public static Plate pcr96(String name) {
        return catalog.createPlate("axygen", "PCR-96-C", name);
    }

    /**
     * Creates an Axygen P-384-SQ-C 384-well plate.
     *
     * High-density 384-well plate format with square well design for increased throughput.
     *
     * @param name the unique instance name for the plate
     * @return a new Axygen P-384-SQ-C plate
     */
    public static Plate p384SqC(String name) {
        return catalog.createPlate("axygen", "P-384-SQ-C", name);
    }
}
