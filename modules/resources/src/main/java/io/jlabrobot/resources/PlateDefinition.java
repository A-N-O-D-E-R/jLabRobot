package io.jlabrobot.resources;

import io.jlabrobot.core.Volume;

/**
 * Defines the specifications for a microplate from a specific vendor.
 *
 * PlateDefinition is a data record that captures all the physical and operational
 * characteristics of a plate model, enabling the creation of plate instances with
 * consistent properties. Definitions are typically loaded from a catalog and can be
 * used to instantiate plates with standardized parameters.
 *
 * @param name the product name or model identifier
 * @param vendor the name of the vendor/manufacturer
 * @param rows the number of rows in the plate grid
 * @param columns the number of columns in the plate grid
 * @param wellSpacingMm the spacing between well centers in millimeters
 * @param wellVolumeMl the maximum volume capacity of each well in milliliters
 * @param material the material composition of the plate (e.g., polypropylene, polystyrene)
 * @param description a brief description of the plate's intended use or features
 */
public record PlateDefinition(
    String name,
    String vendor,
    int rows,
    int columns,
    double wellSpacingMm,
    double wellVolumeMl,
    String material,
    String description
) {
    /**
     * Creates a new Plate instance based on this definition.
     *
     * @param instanceName the unique name to assign to the created plate instance
     * @return a new Plate with properties specified by this definition
     */
    public Plate createPlate(String instanceName) {
        return new Plate(
            instanceName,
            rows,
            columns,
            wellSpacingMm,
            Volume.ml(wellVolumeMl)
        );
    }
}
