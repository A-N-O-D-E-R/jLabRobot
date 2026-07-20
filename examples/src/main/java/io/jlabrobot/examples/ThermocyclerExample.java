package io.jlabrobot.examples;

import io.jlabrobot.thermocycler.Thermocycler;
import io.jlabrobot.thermocycler.inheco.InhecoBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThermocyclerExample {
    private static final Logger log = LoggerFactory.getLogger(ThermocyclerExample.class);

    public static void main(String[] args) throws Exception {
        log.info("=== Thermocycler PCR Demo ===\n");

        Thermocycler tc = new Thermocycler(new InhecoBackend());
        tc.setup();

        log.info("--- Initial Denaturation (95°C, 3 min) ---");
        tc.setBlockTemperature(95.0);
        Thread.sleep(180000);

        log.info("\n--- PCR Cycling (30 cycles) ---");
        tc.runPCRCycle(
            30,
            95.0, 55.0, 72.0,
            30000, 30000, 60000
        );

        log.info("\n--- Final Extension (72°C, 5 min) ---");
        tc.setBlockTemperature(72.0);
        Thread.sleep(300000);

        log.info("\n--- Cool Down (4°C hold) ---");
        tc.setBlockTemperature(4.0);

        log.info("\n--- Opening Lid ---");
        tc.openLid();

        tc.deactivate();
        tc.stop();

        log.info("\n=== PCR Complete ===");
        log.info("Total: 1 init + 30 cycles + 1 extension = ~2.5 hours");
    }
}
