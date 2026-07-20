package io.jlabrobot.examples;

import io.jlabrobot.incubator.Incubator;
import io.jlabrobot.incubator.cytomat.CytomatBackend;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncubatorExample {
    private static final Logger log = LoggerFactory.getLogger(IncubatorExample.class);

    public static void main(String[] args) throws Exception {
        log.info("=== Incubator Cell Culture Demo ===\n");

        Incubator inc = new Incubator(new CytomatBackend(), 20);
        inc.setup();

        log.info("--- Setting Culture Conditions ---");
        inc.setTemperature(37.0);
        inc.setCO2(5.0);
        inc.setHumidity(95.0);

        Plate plate1 = Plate.createPlate96("HEK293-passage3");
        Plate plate2 = Plate.createPlate96("CHO-K1-clones");
        Plate plate3 = Plate.createPlate96("drug-screen-1");

        log.info("\n--- Loading Plates ---");
        inc.loadPlate(plate1, 0);
        inc.loadPlate(plate2, 1);
        inc.loadPlate(plate3, 5);

        log.info("\n--- Incubating for 24 hours ---");
        log.info("Temperature: {}°C", inc.getTemperature());
        log.info("CO2: {}%", inc.getCO2());
        Thread.sleep(5000);

        log.info("\n--- Unloading Plates ---");
        inc.unloadPlate(0);
        inc.unloadPlate(1);
        inc.unloadPlate(5);

        log.info("\n--- Quick Incubation (plate4, 2 hours) ---");
        Plate plate4 = Plate.createPlate96("fixation-test");
        inc.incubate(plate4, 10, 37.0, 5.0, 7200000);

        inc.stop();

        log.info("\n=== Incubation Complete ===");
        log.info("Incubator supports {} slots for parallel culture experiments", inc.getNumSlots());
    }
}
