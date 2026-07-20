package io.jlabrobot.resources.vendor.greiner;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;
import java.io.IOException;

public class Greiner {
    private static final ResourceCatalog catalog = new ResourceCatalog();
    
    static {
        try {
            catalog.loadCatalog("greiner");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Greiner catalog", e);
        }
    }
    
    public static Plate cellstar96(String name) {
        return catalog.createPlate("greiner", "655101", name);
    }
    
    public static Plate microplate384(String name) {
        return catalog.createPlate("greiner", "651201", name);
    }
    
    public static Plate masterblock96(String name) {
        return catalog.createPlate("greiner", "780201", name);
    }
}
