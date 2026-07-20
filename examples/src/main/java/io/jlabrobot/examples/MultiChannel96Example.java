package io.jlabrobot.examples;

import io.jlabrobot.backend.chatterbox.ChatterboxBackend;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.LiquidHandler96;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import io.jlabrobot.resources.ResourceCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiChannel96Example {
    private static final Logger log = LoggerFactory.getLogger(MultiChannel96Example.class);

    public static void main(String[] args) throws Exception {
        log.info("=== 96-Channel Multi-Channel Operations Demo ===\n");

        ResourceCatalog catalog = new ResourceCatalog();
        catalog.loadCatalog("corning");

        Deck deck = new Deck("main-deck");
        TipRack tips = TipRack.createTipRack96("tips");
        Plate source = catalog.createPlate("corning", "3596", "source");
        Plate dest1 = catalog.createPlate("corning", "3596", "dest1");
        Plate dest2 = catalog.createPlate("corning", "3596", "dest2");

        deck.addChild(tips);
        deck.addChild(source);
        deck.addChild(dest1);
        deck.addChild(dest2);

        ChatterboxBackend backend = new ChatterboxBackend();
        LiquidHandler96 lh = new LiquidHandler96(deck, backend);
        lh.initialize();

        log.info("--- Full 96-Well Transfer ---");
        lh.pickUpTips96(tips);
        lh.transfer96(source, dest1, Volume.ul(100));
        lh.dropTips96();

        log.info("\n--- Column-by-Column Transfer ---");
        lh.pickUpTips96(tips);
        for (int col = 1; col <= 12; col++) {
            log.info("Processing column {}", col);
            lh.aspirateColumn(source, col, Volume.ul(50));
            lh.dispenseColumn(dest2, col, Volume.ul(50));
        }
        lh.dropTips96();

        log.info("\n--- Row-by-Row Transfer ---");
        lh.pickUpTips96(tips);
        for (char row = 'A'; row <= 'H'; row++) {
            log.info("Processing row {}", row);
            lh.aspirateRow(source, row, Volume.ul(25));
            lh.dispenseRow(dest1, row, Volume.ul(25));
        }
        lh.dropTips96();

        log.info("\n--- Selective Column Transfer (columns 1, 6, 12) ---");
        lh.aspirateColumn(source, 1, Volume.ul(75));
        lh.dispenseColumn(dest2, 1, Volume.ul(75));

        lh.aspirateColumn(source, 6, Volume.ul(75));
        lh.dispenseColumn(dest2, 6, Volume.ul(75));

        lh.aspirateColumn(source, 12, Volume.ul(75));
        lh.dispenseColumn(dest2, 12, Volume.ul(75));

        lh.shutdown();

        log.info("\n=== Demo Complete ===");
        log.info("96-channel operations enable high-throughput plate replication,");
        log.info("column/row selective transfers, and efficient serial dilutions.");
    }
}
