package io.jlabrobot.resources.vendor.thermofisher;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

public class ThermoFisher {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("thermofisher");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Thermo Fisher catalog", e);
        }
    }

    public static Plate nunc96MicroWell(String name) {
        return catalog.createPlate("thermofisher", "Nunc-96-MicroWell", name);
    }

    public static Plate nunc384MicroWell(String name) {
        return catalog.createPlate("thermofisher", "Nunc-384-MicroWell", name);
    }

    public static Plate nuncDeepWell96(String name) {
        return catalog.createPlate("thermofisher", "Nunc-DeepWell-96", name);
    }

    public static Plate microAmpPCR96(String name) {
        return catalog.createPlate("thermofisher", "MicroAmp-PCR-96", name);
    }

    public static Plate gibcoCellCulture96(String name) {
        return catalog.createPlate("thermofisher", "Gibco-Cell-Culture-96", name);
    }
}
