package io.jlabrobot.resources.vendor.eppendorf;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.ResourceCatalog;
import java.io.IOException;

public class Eppendorf {
    private static final ResourceCatalog catalog = new ResourceCatalog();
    
    static {
        try {
            catalog.loadCatalog("eppendorf");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Eppendorf catalog", e);
        }
    }
    
    public static Plate twinTecPCR96(String name) {
        return catalog.createPlate("eppendorf", "twin.tec_PCR_96", name);
    }
    
    public static Plate deepWell96(String name) {
        return catalog.createPlate("eppendorf", "deepwell_96_2000ul", name);
    }
    
    public static Plate microplate96(String name) {
        return catalog.createPlate("eppendorf", "microplate_96", name);
    }
}
