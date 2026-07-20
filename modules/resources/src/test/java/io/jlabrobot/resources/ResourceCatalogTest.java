package io.jlabrobot.resources;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceCatalogTest {
    @Test
    void testLoadCorningCatalog() throws Exception {
        ResourceCatalog catalog = new ResourceCatalog();
        catalog.loadCatalog("corning");
        
        PlateDefinition def = catalog.getPlate("corning", "3596");
        assertNotNull(def);
        assertEquals("corning", def.vendor());
        assertEquals(8, def.rows());
        assertEquals(12, def.columns());
        assertEquals(0.36, def.wellVolumeMl());
    }
    
    @Test
    void testCreatePlateFromCatalog() throws Exception {
        ResourceCatalog catalog = new ResourceCatalog();
        catalog.loadCatalog("corning");
        
        Plate plate = catalog.createPlate("corning", "3596", "test_plate");
        assertNotNull(plate);
        assertEquals("test_plate", plate.getName());
        assertEquals(96, plate.getAllItems().size());
    }
    
    @Test
    void testMultipleVendors() throws Exception {
        ResourceCatalog catalog = new ResourceCatalog();
        catalog.loadCatalog("corning");
        catalog.loadCatalog("eppendorf");
        catalog.loadCatalog("greiner");
        
        assertNotNull(catalog.getPlate("corning", "3596"));
        assertNotNull(catalog.getPlate("eppendorf", "twin.tec_PCR_96"));
        assertNotNull(catalog.getPlate("greiner", "655101"));
    }
}
