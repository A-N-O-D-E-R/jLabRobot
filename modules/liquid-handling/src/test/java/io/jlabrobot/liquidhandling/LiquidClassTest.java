package io.jlabrobot.liquidhandling;

import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.liquidclass.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LiquidClassTest {
    @Test
    void testWaterLiquidClass() {
        LiquidClass water = LiquidClass.water();
        assertEquals("Water", water.name());
        assertEquals(150.0, water.aspirationFlowRate());
        assertEquals(150.0, water.dispenseFlowRate());
    }
    
    @Test
    void testSerumLiquidClass() {
        LiquidClass serum = LiquidClass.serum();
        assertEquals("Serum", serum.name());
        assertTrue(serum.aspirationFlowRate() < LiquidClass.water().aspirationFlowRate());
        assertTrue(serum.aspirationSettlingTime().microliters() > 0);
    }
    
    @Test
    void testAspirateParameters() {
        LiquidClass dmso = LiquidClass.dmso();
        Volume vol = Volume.ul(100);
        
        AspirateParameters params = AspirateParameters.fromLiquidClass(vol, dmso);
        assertEquals(vol, params.volume());
        assertEquals(dmso.aspirationFlowRate(), params.flowRate());
        assertEquals(dmso.airGap(), params.airGap());
    }
    
    @Test
    void testDispenseParameters() {
        LiquidClass ethanol = LiquidClass.ethanol();
        Volume vol = Volume.ul(50);
        
        DispenseParameters params = DispenseParameters.fromLiquidClass(vol, ethanol);
        assertEquals(vol, params.volume());
        assertEquals(ethanol.dispenseFlowRate(), params.flowRate());
        assertEquals(ethanol.blowoutVolume(), params.blowoutVolume());
    }
    
    @Test
    void testParametersWithMixing() {
        LiquidClass water = LiquidClass.water();
        Volume vol = Volume.ul(200);
        
        AspirateParameters aspirate = AspirateParameters.fromLiquidClass(vol, water)
            .withMixing(3);
        assertEquals(3, aspirate.mixCycles());
        
        DispenseParameters dispense = DispenseParameters.fromLiquidClass(vol, water)
            .withMixing(5);
        assertEquals(5, dispense.mixCycles());
    }
}
