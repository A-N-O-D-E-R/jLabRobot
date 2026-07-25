package io.jlabrobot.resources.vendor.eppendorf;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;
import java.io.IOException;

/**
 * Factory for creating Eppendorf brand microplate instances.
 *
 * Eppendorf is a leading provider of laboratory equipment and consumables,
 * offering innovative microplate formats designed for precision and reliability.
 * This class provides factory methods to create commonly used Eppendorf plate models
 * with their standardized specifications.
 */
public class Eppendorf {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("eppendorf");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Eppendorf catalog", e);
        }
    }

    /**
     * Creates an Eppendorf twin.tec PCR 96-well plate.
     *
     * 96-well PCR plate with twin.tec technology for optimized heat transfer and efficiency
     * in polymerase chain reaction applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Eppendorf twin.tec PCR 96 plate
     */
    public static Plate twinTecPCR96(String name) {
        return catalog.createPlate("eppendorf", "twin.tec_PCR_96", name);
    }

    /**
     * Creates an Eppendorf deep well 96-well plate (2000 µL capacity).
     *
     * 96-well deep well plate with 2000 µL well capacity for large volume storage and handling.
     *
     * @param name the unique instance name for the plate
     * @return a new Eppendorf deep well 96 plate
     */
    public static Plate deepWell96(String name) {
        return catalog.createPlate("eppendorf", "deepwell_96_2000ul", name);
    }

    /**
     * Creates an Eppendorf standard microplate 96-well plate.
     *
     * Standard 96-well microplate format for general-purpose laboratory applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Eppendorf microplate 96
     */
    public static Plate microplate96(String name) {
        return catalog.createPlate("eppendorf", "microplate_96", name);
    }
}
