package io.jlabrobot.visualization;

import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsciiDeckRendererTest {
    @Test
    void renderEmptyDeck() {
        Deck deck = new Deck("test");
        String result = AsciiDeckRenderer.render(deck, 100, 100);
        assertNotNull(result);
        assertTrue(result.contains("."));
    }

    @Test
    void renderDeckWithPlate() {
        Deck deck = new Deck("test");
        Plate plate = Plate.createPlate96("plate");
        deck.addChild(plate);

        String result = AsciiDeckRenderer.render(deck, 150, 100);
        assertTrue(result.contains("o")); // wells rendered
    }
}
