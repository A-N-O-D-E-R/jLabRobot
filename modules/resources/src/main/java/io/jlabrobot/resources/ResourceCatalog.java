package io.jlabrobot.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceCatalog {
    private static final Logger log = LoggerFactory.getLogger(ResourceCatalog.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private final Map<String, PlateDefinition> plates = new HashMap<>();
    
    public void loadCatalog(String vendor) throws IOException {
        String catalogPath = "/catalogs/" + vendor + "/plates.json";
        log.info("Loading catalog: {}", catalogPath);
        
        try (InputStream is = getClass().getResourceAsStream(catalogPath)) {
            if (is == null) {
                throw new IOException("Catalog not found: " + catalogPath);
            }
            
            CatalogData data = mapper.readValue(is, CatalogData.class);
            for (PlateDefinition def : data.plates) {
                String key = vendor + "/" + def.name();
                plates.put(key, def);
                log.debug("Loaded plate: {}", key);
            }
        }
    }
    
    public PlateDefinition getPlate(String vendor, String name) {
        String key = vendor + "/" + name;
        PlateDefinition def = plates.get(key);
        if (def == null) {
            throw new IllegalArgumentException("Unknown plate: " + key);
        }
        return def;
    }
    
    public Plate createPlate(String vendor, String name, String instanceName) {
        return getPlate(vendor, name).createPlate(instanceName);
    }
    
    private static class CatalogData {
        public List<PlateDefinition> plates;
    }
}
