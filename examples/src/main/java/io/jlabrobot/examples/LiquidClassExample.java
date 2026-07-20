package io.jlabrobot.examples;

import io.jlabrobot.backend.chatterbox.ChatterboxBackend;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandlerWithClasses;
import io.jlabrobot.liquidhandling.liquidclass.*;
import io.jlabrobot.resources.*;
import io.jlabrobot.resources.vendor.corning.Corning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LiquidClassExample {
    private static final Logger log = LoggerFactory.getLogger(LiquidClassExample.class);
    
    public static void main(String[] args) throws Exception {
        log.info("=== Liquid Class Demo ===\n");
        
        // Setup deck
        Deck deck = new Deck("deck");
        Plate source = Corning.plate3596("source");
        Plate dest = Corning.plate3596("dest");
        deck.addChild(source);
        deck.addChild(dest);
        
        // Create handler with liquid class support
        LiquidHandlerWithClasses lh = new LiquidHandlerWithClasses(
            deck,
            new ChatterboxBackend()
        );
        lh.initialize();
        
        // Demo different liquid classes
        log.info("--- Water Transfer ---");
        lh.setLiquidClass(LiquidClass.water());
        AspirateParameters waterAsp = AspirateParameters.fromLiquidClass(
            Volume.ul(100),
            LiquidClass.water()
        );
        log.info("Water aspiration: {}µL/s flow, {}µL air gap",
            waterAsp.flowRate(), waterAsp.airGap().microliters());
        lh.aspirateWithClass(
            List.of(source.getItem(0, 0)),
            List.of(Volume.ul(100))
        );
        lh.dispenseWithClass(
            List.of(dest.getItem(0, 0)),
            List.of(Volume.ul(100))
        );
        
        log.info("\n--- Serum Transfer (viscous) ---");
        lh.setLiquidClass(LiquidClass.serum());
        AspirateParameters serumAsp = AspirateParameters.fromLiquidClass(
            Volume.ul(100),
            LiquidClass.serum()
        );
        log.info("Serum aspiration: {}µL/s flow (slower), {}µL air gap (larger)",
            serumAsp.flowRate(), serumAsp.airGap().microliters());
        log.info("Settling time: {}µL", serumAsp.settlingTime().microliters());
        lh.aspirateWithClass(
            List.of(source.getItem(1, 0)),
            List.of(Volume.ul(50))
        );
        lh.dispenseWithClass(
            List.of(dest.getItem(1, 0)),
            List.of(Volume.ul(50))
        );
        
        log.info("\n--- DMSO Transfer (volatile) ---");
        lh.setLiquidClass(LiquidClass.dmso());
        AspirateParameters dmsoAsp = AspirateParameters.fromLiquidClass(
            Volume.ul(100),
            LiquidClass.dmso()
        );
        log.info("DMSO aspiration: {}µL/s flow (very slow), {}µL air gap (largest)",
            dmsoAsp.flowRate(), dmsoAsp.airGap().microliters());
        lh.aspirateWithClass(
            List.of(source.getItem(2, 0)),
            List.of(Volume.ul(25))
        );
        lh.dispenseWithClass(
            List.of(dest.getItem(2, 0)),
            List.of(Volume.ul(25))
        );
        
        log.info("\n--- Ethanol Transfer ---");
        lh.setLiquidClass(LiquidClass.ethanol());
        DispenseParameters ethanolDisp = DispenseParameters.fromLiquidClass(
            Volume.ul(100),
            LiquidClass.ethanol()
        );
        log.info("Ethanol dispense: {}µL/s flow, {} blowout volume",
            ethanolDisp.flowRate(), ethanolDisp.blowoutVolume());
        lh.aspirateWithClass(
            List.of(source.getItem(3, 0)),
            List.of(Volume.ul(75))
        );
        lh.dispenseWithClass(
            List.of(dest.getItem(3, 0)),
            List.of(Volume.ul(75))
        );
        
        lh.shutdown();
        log.info("\n=== Demo Complete ===");
        log.info("Liquid classes adapt flow rates, air gaps, and settling times");
        log.info("for accurate handling of different fluid types.");
    }
}
