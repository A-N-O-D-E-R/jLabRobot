package io.jlabrobot.liquidhandling.liquidclass;

import io.jlabrobot.core.Volume;

/**
 * Parameters for dispense operations customized for a specific liquid type.
 * Includes flow rate, settling time, blowout volume, and optional mixing cycles.
 */
public record DispenseParameters(
    Volume volume,
    double flowRate,
    Volume settlingTime,
    double blowoutVolume,
    double mixFlowRate,
    int mixCycles
) {
    /**
     * Creates dispense parameters derived from a liquid class.
     * @param volume the volume to dispense
     * @param lc the liquid class defining the parameters
     * @return the dispense parameters
     */
    public static DispenseParameters fromLiquidClass(Volume volume, LiquidClass lc) {
        return new DispenseParameters(
            volume,
            lc.dispenseFlowRate(),
            lc.dispenseSettlingTime(),
            lc.blowoutVolume(),
            lc.dispenseMixFlowRate(),
            0
        );
    }

    /**
     * Returns new parameters with the specified number of mixing cycles.
     * @param cycles the number of mixing cycles
     * @return new parameters with mixing cycles set
     */
    public DispenseParameters withMixing(int cycles) {
        return new DispenseParameters(
            volume,
            flowRate,
            settlingTime,
            blowoutVolume,
            mixFlowRate,
            cycles
        );
    }
}
