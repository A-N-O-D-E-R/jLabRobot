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

public class CherryPickingExample {
    public static void main(String[] args) throws BackendException {
        Deck deck = new Deck("MainDeck");

        TipRack tipRack = TipRack.createTipRack96("tips");
        deck.addChild(tipRack);

        Plate sourcePlate = Plate.createPlate96("source");
        deck.addChild(sourcePlate);

        Plate destPlate = Plate.createPlate96("dest");
        deck.addChild(destPlate);

        // Fill source wells with samples
        sourcePlate.getAllItems().forEach(w -> w.setCurrentVolume(new Volume(100)));

        Backend backend = new MockBackend();
        LiquidHandler lh = new LiquidHandler(deck, backend);

        lh.initialize();

        // Cherry pick specific wells: A1→A1, C3→A2, E5→A3, G7→A4
        int[][] pickList = {{0, 0}, {2, 2}, {4, 4}, {6, 6}};

        List<Tip> tip = List.of(tipRack.getItem(0, 0));
        lh.pickUpTips(tip);

        for (int i = 0; i < pickList.length; i++) {
            Well source = sourcePlate.getItem(pickList[i][0], pickList[i][1]);
            Well dest = destPlate.getItem(0, i);

            System.out.printf("Picking %c%d → A%d\n",
                    (char) ('A' + pickList[i][0]), pickList[i][1] + 1, i + 1);

            lh.aspirate(List.of(source), List.of(25.0));
            lh.dispense(List.of(dest), List.of(25.0));
        }

        lh.dropTips(tip);
        lh.shutdown();

        System.out.println("\nCherry picking complete!");
        System.out.println("Transferred 25µL from 4 selected wells to dest plate row A");
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
