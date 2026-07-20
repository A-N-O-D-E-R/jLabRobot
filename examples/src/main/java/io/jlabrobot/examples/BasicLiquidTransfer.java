package io.jlabrobot.examples;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandler;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import io.jlabrobot.resources.Tip;
import io.jlabrobot.resources.Well;

import java.util.List;

public class BasicLiquidTransfer {
    public static void main(String[] args) throws BackendException {
        Deck deck = new Deck("MainDeck");

        TipRack tipRack = TipRack.createTipRack96("tips");
        deck.addChild(tipRack);

        Plate sourcePlate = Plate.createPlate96("source");
        deck.addChild(sourcePlate);

        Plate destPlate = Plate.createPlate96("dest");
        deck.addChild(destPlate);

        Backend mockBackend = new MockBackend();
        LiquidHandler lh = new LiquidHandler(deck, mockBackend);

        lh.initialize();

        List<Tip> tips = List.of(tipRack.getItem(0, 0));
        lh.pickUpTips(tips);

        Well sourceWell = sourcePlate.getItem(0, 0);
        Well destWell = destPlate.getItem(0, 0);

        sourceWell.setCurrentVolume(new Volume(100));

        lh.aspirate(List.of(sourceWell), List.of(50.0));
        lh.dispense(List.of(destWell), List.of(50.0));

        lh.dropTips(tips);

        lh.shutdown();

        System.out.println("Transfer complete!");
        System.out.println("Source well volume: " + sourceWell.getCurrentVolume().microliters() + " µL");
        System.out.println("Dest well volume: " + destWell.getCurrentVolume().microliters() + " µL");
    }

    static class MockBackend implements Backend {
        @Override
        public void initialize() {
            System.out.println("Mock backend initialized");
        }

        @Override
        public CommandResult executeCommand(Command cmd) {
            System.out.println("Mock executing: " + cmd.name());
            return CommandResult.success("OK");
        }

        @Override
        public void shutdown() {
            System.out.println("Mock backend shutdown");
        }
    }
}
