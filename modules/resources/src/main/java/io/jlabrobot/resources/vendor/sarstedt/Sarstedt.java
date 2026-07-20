package io.jlabrobot.resources.vendor.sarstedt;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;

import java.io.IOException;

public class Sarstedt {
    private static final ResourceCatalog catalog = new ResourceCatalog();

    static {
        try {
            catalog.loadCatalog("sarstedt");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Sarstedt catalog", e);
        }
    }

    public static Plate plate821582001(String name) {
        return catalog.createPlate("sarstedt", "82.1582.001", name);
    }

    public static Plate plate821583001(String name) {
        return catalog.createPlate("sarstedt", "82.1583.001", name);
    }

    public static Plate deepWell821920500(String name) {
        return catalog.createPlate("sarstedt", "82.1920.500", name);
    }
}
