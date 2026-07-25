package io.jlabrobot.liquidhandling.liquidclass;

import io.jlabrobot.core.Volume;

/**
 * Defines liquid handling parameters for different types of liquids (water, serum, DMSO, etc.).
 * Parameters include flow rates, settling times, and air gaps to optimize pipetting for each liquid type.
 */
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
    /**
     * Validates that flow rates are positive.
     * @throws IllegalArgumentException if flow rates are not positive
     */
    public LiquidClass {
        if (aspirationFlowRate <= 0 || dispenseFlowRate <= 0) {
            throw new IllegalArgumentException("Flow rates must be positive");
        }
    }

    /**
     * Creates a liquid class for standard aqueous solutions (water).
     * @return the water liquid class
     */
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

    /**
     * Creates a liquid class for high viscosity biological fluids (serum).
     * @return the serum liquid class
     */
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

    /**
     * Creates a liquid class for DMSO (dimethyl sulfoxide).
     * @return the DMSO liquid class
     */
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

    /**
     * Creates a liquid class for ethanol (volatile alcohol).
     * @return the ethanol liquid class
     */
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
