package io.jlabrobot.examples;

import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.visualization.AsciiDeckRenderer;

public class VisualizationExample {
    public static void main(String[] args) {
        Deck deck = new Deck("MainDeck");

        Plate plate = Plate.createPlate96("plate1");
        deck.addChild(plate);

        System.out.println("ASCII Deck Visualization:");
        System.out.println("'#' = carrier (deck, plate), 'o' = well/tip\n");
        System.out.println(AsciiDeckRenderer.render(deck, 150, 100));
    }
}
