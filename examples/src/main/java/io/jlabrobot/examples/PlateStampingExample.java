package io.jlabrobot.examples;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandler96;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;

import java.util.List;

public class PlateStampingExample {
    public static void main(String[] args) throws BackendException {
        Deck deck = new Deck("MainDeck");

        TipRack tipRack = TipRack.createTipRack96("tips");
        deck.addChild(tipRack);

        Plate sourcePlate = Plate.createPlate96("source");
        deck.addChild(sourcePlate);

        Plate destPlate = Plate.createPlate96("dest");
        deck.addChild(destPlate);

        // Fill source plate
        sourcePlate.getAllItems().forEach(w -> w.setCurrentVolume(new Volume(100)));

        Backend backend = new MockBackend();
        LiquidHandler96 lh = new LiquidHandler96(deck, backend);

        lh.initialize();

        // Stamp entire plate with 96-head
        lh.pickUpTips96(tipRack);
        lh.aspirate96(sourcePlate, new Volume(50));
        lh.dispense96(destPlate, new Volume(50));
        lh.dropTips96();

        lh.shutdown();

        System.out.println("Plate stamping complete!");
        System.out.println("Transferred 50µL from all 96 wells");
    }

    static class MockBackend implements Backend {
        @Override
        public void initialize() {}

        @Override
        public CommandResult executeCommand(Command cmd) {
            System.out.println("Executing 96-channel command: " + cmd.name());
            return CommandResult.success("OK");
        }

        @Override
        public void shutdown() {}
    }
}
