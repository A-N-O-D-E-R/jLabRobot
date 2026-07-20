package io.jlabrobot.liquidhandling.liquidclass;

import io.jlabrobot.core.Volume;

public record DispenseParameters(
    Volume volume,
    double flowRate,
    Volume settlingTime,
    double blowoutVolume,
    double mixFlowRate,
    int mixCycles
) {
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
