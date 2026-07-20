package io.jlabrobot.liquidhandling;

import io.jlabrobot.backend.chatterbox.ChatterboxBackend;
import io.jlabrobot.core.Volume;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LiquidHandler96Test {

    private LiquidHandler96 handler;
    private Deck deck;
    private TipRack tipRack;
    private Plate source;
    private Plate dest;

    @BeforeEach
    void setup() throws Exception {
        deck = new Deck("test-deck");
        ChatterboxBackend backend = new ChatterboxBackend();

        tipRack = TipRack.createTipRack96("tips");
        source = Plate.createPlate96("source");
        dest = Plate.createPlate96("dest");

        deck.addChild(tipRack);
        deck.addChild(source);
        deck.addChild(dest);

        handler = new LiquidHandler96(deck, backend);
        handler.initialize();
    }

    @Test
    void testPickUpAndDrop96() throws Exception {
        handler.pickUpTips96(tipRack);
        handler.dropTips96();
    }

    @Test
    void testAspirate96() throws Exception {
        handler.pickUpTips96(tipRack);
        handler.aspirate96(source, Volume.ul(100));
        handler.dropTips96();
    }

    @Test
    void testDispense96() throws Exception {
        handler.pickUpTips96(tipRack);
        handler.aspirate96(source, Volume.ul(100));
        handler.dispense96(dest, Volume.ul(100));
        handler.dropTips96();
    }

    @Test
    void testTransfer96() throws Exception {
        handler.pickUpTips96(tipRack);
        handler.transfer96(source, dest, Volume.ul(50));
        handler.dropTips96();
    }

    @Test
    void testColumnOperations() throws Exception {
        handler.aspirateColumn(source, 1, Volume.ul(100));
        handler.dispenseColumn(dest, 1, Volume.ul(100));
    }

    @Test
    void testRowOperations() throws Exception {
        handler.aspirateRow(source, 'A', Volume.ul(100));
        handler.dispenseRow(dest, 'A', Volume.ul(100));
    }

    @Test
    void testMultipleColumns() throws Exception {
        handler.pickUpTips96(tipRack);

        for (int col = 1; col <= 12; col++) {
            handler.aspirateColumn(source, col, Volume.ul(50));
            handler.dispenseColumn(dest, col, Volume.ul(50));
        }

        handler.dropTips96();
    }
}
