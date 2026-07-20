package io.jlabrobot.examples;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.washer.Washer;
import io.jlabrobot.washer.biotek.BioTekBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WasherExample {
    private static final Logger log = LoggerFactory.getLogger(WasherExample.class);

    public static void main(String[] args) throws Exception {
        log.info("=== Plate Washer ELISA Demo ===\n");

        Washer washer = new Washer(new BioTekBackend());
        washer.setup();

        log.info("--- Priming Washer ---");
        washer.prime();

        Plate elisa1 = Plate.createPlate96("ELISA-antibody");
        Plate elisa2 = Plate.createPlate96("ELISA-blocking");
        Plate elisa3 = Plate.createPlate96("ELISA-detection");

        log.info("\n--- Washing Plate 1 (after antibody coating) ---");
        washer.washAndUnload(elisa1, 3, 0.3);

        log.info("\n--- Washing Plate 2 (after blocking) ---");
        washer.loadPlate(elisa2);
        washer.wash(5, 0.3);
        washer.unloadPlate();

        log.info("\n--- Washing Plate 3 (final wash before detection) ---");
        washer.washAndUnload(elisa3, 4, 0.35);

        washer.stop();

        log.info("\n=== Washing Complete ===");
        log.info("Typical ELISA protocol: 3-5 wash cycles between each step");
    }
}
