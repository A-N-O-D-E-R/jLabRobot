package io.jlabrobot.resources;

import io.jlabrobot.core.Volume;

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
