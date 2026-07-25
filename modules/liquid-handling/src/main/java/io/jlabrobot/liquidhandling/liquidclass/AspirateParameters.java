package io.jlabrobot.liquidhandling.liquidclass;

import io.jlabrobot.core.Volume;

/**
 * Parameters for aspiration operations customized for a specific liquid type.
 * Includes flow rate, air gap, settling time, and optional mixing cycles.
 */
public record AspirateParameters(
    Volume volume,
    double flowRate,
    Volume airGap,
    Volume settlingTime,
    double mixFlowRate,
    int mixCycles
) {
    /**
     * Creates aspiration parameters derived from a liquid class.
     * @param volume the volume to aspirate
     * @param lc the liquid class defining the parameters
     * @return the aspiration parameters
     */
    public static AspirateParameters fromLiquidClass(Volume volume, LiquidClass lc) {
        return new AspirateParameters(
            volume,
            lc.aspirationFlowRate(),
            lc.airGap(),
            lc.aspirationSettlingTime(),
            lc.aspirationMixFlowRate(),
            0
        );
    }

    /**
     * Returns new parameters with the specified number of mixing cycles.
     * @param cycles the number of mixing cycles
     * @return new parameters with mixing cycles set
     */
    public AspirateParameters withMixing(int cycles) {
        return new AspirateParameters(
            volume,
            flowRate,
            airGap,
            settlingTime,
            mixFlowRate,
            cycles
        );
    }
}
