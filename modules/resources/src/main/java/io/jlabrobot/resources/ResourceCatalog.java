package io.jlabrobot.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a catalog of plate definitions loaded from JSON resources.
 *
 * ResourceCatalog provides access to plate specifications for different vendors
 * by loading them from JSON files packaged with the application. It caches loaded
 * plate definitions in memory for quick lookup and creation of plate instances.
 * Plates are identified by a composite key combining vendor name and plate model.
 */
public class ResourceCatalog {
    private static final Logger log = LoggerFactory.getLogger(ResourceCatalog.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, PlateDefinition> plates = new HashMap<>();

    /**
     * Loads all plate definitions for a specific vendor from a JSON catalog resource.
     *
     * Catalog files are expected to be located in the classpath at {@code /catalogs/{vendor}/plates.json}.
     * Each catalog contains a list of plate definitions which are indexed by vendor name and plate name.
     *
     * @param vendor the vendor name (e.g., "corning", "eppendorf", "greiner")
     * @throws IOException if the catalog file cannot be found or parsed
     */
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
    
    /**
     * Retrieves a plate definition by vendor and model name.
     *
     * @param vendor the vendor name
     * @param name the plate model name
     * @return the PlateDefinition for the specified plate
     * @throws IllegalArgumentException if no plate definition exists for the given vendor and name
     */
    public PlateDefinition getPlate(String vendor, String name) {
        String key = vendor + "/" + name;
        PlateDefinition def = plates.get(key);
        if (def == null) {
            throw new IllegalArgumentException("Unknown plate: " + key);
        }
        return def;
    }

    /**
     * Creates a new plate instance by vendor and model name.
     *
     * @param vendor the vendor name
     * @param name the plate model name
     * @param instanceName the unique name to assign to the created plate
     * @return a new Plate instance with the specified properties
     * @throws IllegalArgumentException if no plate definition exists for the given vendor and name
     */
    public Plate createPlate(String vendor, String name, String instanceName) {
        return getPlate(vendor, name).createPlate(instanceName);
    }

    /**
     * Internal data structure for deserializing plate catalog JSON files.
     */
    private static class CatalogData {
        public List<PlateDefinition> plates;
    }
}
