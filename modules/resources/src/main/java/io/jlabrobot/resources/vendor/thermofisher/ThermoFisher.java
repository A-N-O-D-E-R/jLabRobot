package io.jlabrobot.resources.vendor.thermofisher;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

/**
 * Factory for creating Thermo Fisher brand microplate instances.
 *
 * Thermo Fisher Scientific offers a comprehensive portfolio of microplates under several
 * brands including Nunc, MicroAmp, and Gibco, serving diverse research and diagnostic needs.
 * This class provides factory methods to create commonly used Thermo Fisher plate models
 * with their standardized specifications.
 */
public class ThermoFisher {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("thermofisher");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Thermo Fisher catalog", e);
        }
    }

    /**
     * Creates a Thermo Fisher Nunc 96-well microwell plate.
     *
     * Standard 96-well plate for routine laboratory use with proven reliability.
     *
     * @param name the unique instance name for the plate
     * @return a new Thermo Fisher Nunc 96-well plate
     */
    public static Plate nunc96MicroWell(String name) {
        return catalog.createPlate("thermofisher", "Nunc-96-MicroWell", name);
    }

    /**
     * Creates a Thermo Fisher Nunc 384-well microwell plate.
     *
     * High-density 384-well plate for increased throughput screening and analysis.
     *
     * @param name the unique instance name for the plate
     * @return a new Thermo Fisher Nunc 384-well plate
     */
    public static Plate nunc384MicroWell(String name) {
        return catalog.createPlate("thermofisher", "Nunc-384-MicroWell", name);
    }

    /**
     * Creates a Thermo Fisher Nunc 96-well deep well plate.
     *
     * 96-well deep well plate for large volume storage and handling applications.
     *
     * @param name the unique instance name for the plate
     * @return a new Thermo Fisher Nunc deep well plate
     */
    public static Plate nuncDeepWell96(String name) {
        return catalog.createPlate("thermofisher", "Nunc-DeepWell-96", name);
    }

    /**
     * Creates a Thermo Fisher MicroAmp PCR 96-well plate.
     *
     * 96-well PCR plate optimized for real-time and standard polymerase chain reaction.
     *
     * @param name the unique instance name for the plate
     * @return a new Thermo Fisher MicroAmp PCR plate
     */
    public static Plate microAmpPCR96(String name) {
        return catalog.createPlate("thermofisher", "MicroAmp-PCR-96", name);
    }

    /**
     * Creates a Thermo Fisher Gibco cell culture 96-well plate.
     *
     * 96-well plate specially designed for cell culture applications with optimized surface treatment.
     *
     * @param name the unique instance name for the plate
     * @return a new Thermo Fisher Gibco cell culture plate
     */
    public static Plate gibcoCellCulture96(String name) {
        return catalog.createPlate("thermofisher", "Gibco-Cell-Culture-96", name);
    }
}
