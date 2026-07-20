package io.jlabrobot.examples;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandler;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import io.jlabrobot.resources.Tip;
import io.jlabrobot.resources.Well;

import java.util.List;

public class SerialDilutionExample {
    public static void main(String[] args) throws BackendException {
        Deck deck = new Deck("MainDeck");

        TipRack tipRack = TipRack.createTipRack96("tips");
        deck.addChild(tipRack);

        Plate dilutionPlate = Plate.createPlate96("dilutions");
        deck.addChild(dilutionPlate);

        Backend backend = new MockBackend();
        LiquidHandler lh = new LiquidHandler(deck, backend);

        lh.initialize();

        // Setup: add buffer to wells B-H in column 1
        for (int row = 1; row < 8; row++) {
            dilutionPlate.getItem(row, 0).setCurrentVolume(new Volume(90));
        }

        // Add stock to well A1
        dilutionPlate.getItem(0, 0).setCurrentVolume(new Volume(100));

        // Serial dilution: transfer 10µL down column, mix
        List<Tip> tip = List.of(tipRack.getItem(0, 0));
        lh.pickUpTips(tip);

        for (int row = 0; row < 7; row++) {
            Well source = dilutionPlate.getItem(row, 0);
            Well dest = dilutionPlate.getItem(row + 1, 0);

            lh.aspirate(List.of(source), List.of(10.0));
            lh.dispense(List.of(dest), List.of(10.0));

            // Mix
            for (int i = 0; i < 3; i++) {
                lh.aspirate(List.of(dest), List.of(50.0));
                lh.dispense(List.of(dest), List.of(50.0));
            }
        }

        lh.dropTips(tip);
        lh.shutdown();

        System.out.println("Serial dilution complete!");
        System.out.println("Final volumes:");
        for (int row = 0; row < 8; row++) {
            System.out.printf("  Row %c: %.1f µL\n",
                    (char) ('A' + row),
                    dilutionPlate.getItem(row, 0).getCurrentVolume().microliters());
        }
    }

    static class MockBackend implements Backend {
        @Override
        public void initialize() {}

        @Override
        public CommandResult executeCommand(Command cmd) {
            return CommandResult.success("OK");
        }

        @Override
        public void shutdown() {}
    }
}
