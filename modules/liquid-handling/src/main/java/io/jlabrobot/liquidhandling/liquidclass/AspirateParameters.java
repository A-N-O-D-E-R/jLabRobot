package io.jlabrobot.liquidhandling.liquidclass;

import io.jlabrobot.core.Volume;

public record AspirateParameters(
    Volume volume,
    double flowRate,
    Volume airGap,
    Volume settlingTime,
    double mixFlowRate,
    int mixCycles
) {
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
