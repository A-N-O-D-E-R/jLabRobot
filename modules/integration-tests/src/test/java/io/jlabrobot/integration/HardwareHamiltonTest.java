package io.jlabrobot.integration;

import io.jlabrobot.backend.hamilton.HamiltonBackend;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandler;
import io.jlabrobot.protocol.serial.SerialChannel;
import io.jlabrobot.protocol.serial.SerialPortUtil;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import io.jlabrobot.resources.Tip;
import io.jlabrobot.resources.Well;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Hardware integration tests for Hamilton STAR liquid handler.
 *
 * Prerequisites:
 * - Hamilton STAR connected via serial or TCP
 * - Set HAMILTON_PORT env var (e.g., "COM3" or "/dev/ttyUSB0")
 * - Deck setup: tip rack at position 1, plates at positions 2-3
 *
 * Run with: mvn test -Phardware
 * Or: mvn test -Dtest=HardwareHamiltonTest
 */
@Tag("hardware")
@EnabledIfEnvironmentVariable(named = "HAMILTON_PORT", matches = ".+")
class HardwareHamiltonTest {
    private LiquidHandler liquidHandler;
    private Deck deck;
    private TipRack tipRack;
    private Plate sourcePlate;
    private Plate destPlate;

    @BeforeEach
    void setup() throws Exception {
        String port = System.getenv("HAMILTON_PORT");
        System.out.println("Available ports:");
        SerialPortUtil.printAvailablePorts();
        System.out.println("\nConnecting to Hamilton on: " + port);

        SerialChannel channel = new SerialChannel(38400); // Hamilton typical baud
        HamiltonBackend backend = new HamiltonBackend(channel, port);

        deck = new Deck("HamiltonDeck");

        // ponytail: use actual taught positions when calibration done
        tipRack = TipRack.createTipRack96("tips");
        deck.addChild(tipRack);

        sourcePlate = Plate.createPlate96("source");
        deck.addChild(sourcePlate);

        destPlate = Plate.createPlate96("dest");
        deck.addChild(destPlate);

        liquidHandler = new LiquidHandler(deck, backend);
        liquidHandler.initialize();
    }

    @AfterEach
    void teardown() {
        if (liquidHandler != null) {
            liquidHandler.shutdown();
        }
    }

    @Test
    void testPickUpAndDropTips() throws Exception {
        List<Tip> tips = List.of(tipRack.getItem(0, 0));

        liquidHandler.pickUpTips(tips);
        assertNotNull(liquidHandler.getHead());

        liquidHandler.dropTips(tips);
    }

    @Test
    void testBasicLiquidTransfer() throws Exception {
        // Assumes source well has liquid loaded
        Well sourceWell = sourcePlate.getItem(0, 0);
        Well destWell = destPlate.getItem(0, 0);

        sourceWell.setCurrentVolume(new Volume(100));

        List<Tip> tips = List.of(tipRack.getItem(0, 0));
        liquidHandler.pickUpTips(tips);

        liquidHandler.aspirate(List.of(sourceWell), List.of(50.0));
        liquidHandler.dispense(List.of(destWell), List.of(50.0));

        liquidHandler.dropTips(tips);

        assertEquals(50.0, sourceWell.getCurrentVolume().microliters(), 0.1);
        assertEquals(50.0, destWell.getCurrentVolume().microliters(), 0.1);
    }

    @Test
    void testSerialDilution() throws Exception {
        // Load buffer in wells B-D
        for (int row = 1; row < 4; row++) {
            sourcePlate.getItem(row, 0).setCurrentVolume(new Volume(90));
        }
        sourcePlate.getItem(0, 0).setCurrentVolume(new Volume(100));

        List<Tip> tip = List.of(tipRack.getItem(0, 1));
        liquidHandler.pickUpTips(tip);

        for (int row = 0; row < 3; row++) {
            Well source = sourcePlate.getItem(row, 0);
            Well dest = sourcePlate.getItem(row + 1, 0);

            liquidHandler.aspirate(List.of(source), List.of(10.0));
            liquidHandler.dispense(List.of(dest), List.of(10.0));
        }

        liquidHandler.dropTips(tip);

        // Verify dilution
        assertEquals(90.0, sourcePlate.getItem(0, 0).getCurrentVolume().microliters(), 0.1);
        assertEquals(100.0, sourcePlate.getItem(3, 0).getCurrentVolume().microliters(), 0.1);
    }
}
