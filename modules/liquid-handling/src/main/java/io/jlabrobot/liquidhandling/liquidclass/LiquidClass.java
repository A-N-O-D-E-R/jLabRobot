package io.jlabrobot.liquidhandling.liquidclass;

import io.jlabrobot.core.Volume;

public record LiquidClass(
    String name,
    double aspirationFlowRate,
    double dispenseFlowRate,
    Volume aspirationSettlingTime,
    Volume dispenseSettlingTime,
    double aspirationMixFlowRate,
    double dispenseMixFlowRate,
    Volume airGap,
    double blowoutVolume,
    String description
) {
    public LiquidClass {
        if (aspirationFlowRate <= 0 || dispenseFlowRate <= 0) {
            throw new IllegalArgumentException("Flow rates must be positive");
        }
    }
    
    public static LiquidClass water() {
        return new LiquidClass(
            "Water",
            150.0,
            150.0,
            Volume.ul(0),
            Volume.ul(0),
            200.0,
            200.0,
            Volume.ul(5),
            50.0,
            "Standard aqueous solution"
        );
    }
    
    public static LiquidClass serum() {
        return new LiquidClass(
            "Serum",
            100.0,
            120.0,
            Volume.ul(500),
            Volume.ul(500),
            150.0,
            150.0,
            Volume.ul(10),
            100.0,
            "High viscosity biological fluid"
        );
    }
    
    public static LiquidClass dmso() {
        return new LiquidClass(
            "DMSO",
            50.0,
            50.0,
            Volume.ul(1000),
            Volume.ul(1000),
            75.0,
            75.0,
            Volume.ul(15),
            150.0,
            "Dimethyl sulfoxide - volatile organic solvent"
        );
    }
    
    public static LiquidClass ethanol() {
        return new LiquidClass(
            "Ethanol",
            100.0,
            100.0,
            Volume.ul(200),
            Volume.ul(200),
            150.0,
            150.0,
            Volume.ul(20),
            100.0,
            "Volatile alcohol"
        );
    }
}
