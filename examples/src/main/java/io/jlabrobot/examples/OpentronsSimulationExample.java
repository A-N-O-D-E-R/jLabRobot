package io.jlabrobot.examples;

import io.jlabrobot.backend.opentrons.OpentronsBackend;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandler;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import io.jlabrobot.resources.Tip;
import io.jlabrobot.resources.Well;

import java.util.List;

/**
 * Opentrons simulation example.
 *
 * Prerequisites:
 *   pip install opentrons
 *
 * This example generates a Python protocol script and simulates it
 * using opentrons_simulate command.
 *
 * For physical robot execution, change "simulate" to robot IP address.
 */
public class OpentronsSimulationExample {
    public static void main(String[] args) {
        try {
            Deck deck = new Deck("OpentronsDeck");

            TipRack tipRack = TipRack.createTipRack96("tips");
            deck.addChild(tipRack);

            Plate sourcePlate = Plate.createPlate96("source");
            deck.addChild(sourcePlate);

            Plate destPlate = Plate.createPlate96("dest");
            deck.addChild(destPlate);

            // Set source volumes
            sourcePlate.getAllItems().forEach(w -> w.setCurrentVolume(new Volume(100)));

            // Use "simulate" for simulation mode, or IP address for real robot
            OpentronsBackend backend = new OpentronsBackend("simulate");
            LiquidHandler lh = new LiquidHandler(deck, backend);

            System.out.println("Initializing Opentrons backend (simulation mode)...");
            lh.initialize();

            // Perform liquid transfers
            List<Tip> tip = List.of(tipRack.getItem(0, 0));
            Well sourceWell = sourcePlate.getItem(0, 0);
            Well destWell = destPlate.getItem(0, 1);

            System.out.println("Executing liquid handling protocol...");
            lh.pickUpTips(tip);
            lh.aspirate(List.of(sourceWell), List.of(50.0));
            lh.dispense(List.of(destWell), List.of(50.0));
            lh.dropTips(tip);

            System.out.println("Shutting down (generates and simulates protocol)...");
            lh.shutdown();

            System.out.println("\nProtocol simulation complete!");
            System.out.println("To run on physical robot:");
            System.out.println("  new OpentronsBackend(\"192.168.1.100\")");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nMake sure Opentrons Python package is installed:");
            System.err.println("  pip install opentrons");
            e.printStackTrace();
        }
    }
}
