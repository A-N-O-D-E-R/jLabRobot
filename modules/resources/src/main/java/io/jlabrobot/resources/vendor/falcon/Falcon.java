package io.jlabrobot.resources.vendor.falcon;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

public class Falcon {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("falcon");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Falcon catalog", e);
        }
    }

    public static Plate plate353072(String name) {
        return catalog.createPlate("falcon", "353072", name);
    }

    public static Plate plate353916(String name) {
        return catalog.createPlate("falcon", "353916", name);
    }

    public static Plate plate353293(String name) {
        return catalog.createPlate("falcon", "353293", name);
    }

    public static Plate plate353263(String name) {
        return catalog.createPlate("falcon", "353263", name);
    }
}
