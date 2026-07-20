package io.jlabrobot.resources.vendor.axygen;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

public class Axygen {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("axygen");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Axygen catalog", e);
        }
    }

    public static Plate p96450VC(String name) {
        return catalog.createPlate("axygen", "P-96-450V-C", name);
    }

    public static Plate deepWell2mL(String name) {
        return catalog.createPlate("axygen", "P-DW-2ML", name);
    }

    public static Plate pcr96(String name) {
        return catalog.createPlate("axygen", "PCR-96-C", name);
    }

    public static Plate p384SqC(String name) {
        return catalog.createPlate("axygen", "P-384-SQ-C", name);
    }
}
