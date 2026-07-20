package io.jlabrobot.resources.vendor.corning;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;
import java.io.IOException;

public class Corning {
    private static final ResourceCatalog catalog = new ResourceCatalog();
    
    static {
        try {
            catalog.loadCatalog("corning");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Corning catalog", e);
        }
    }
    
    public static Plate plate3596(String name) {
        return catalog.createPlate("corning", "3596", name);
    }
    
    public static Plate plate3370(String name) {
        return catalog.createPlate("corning", "3370", name);
    }
    
    public static Plate plate3704(String name) {
        return catalog.createPlate("corning", "3704", name);
    }
    
    public static Plate plate3912(String name) {
        return catalog.createPlate("corning", "3912", name);
    }
}
