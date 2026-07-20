package io.jlabrobot.liquidhandling;

import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Volume;
import io.jlabrobot.resources.Tip;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TipTrackerTest {
    @Test
    void testPickupAndDrop() {
        TipTracker tracker = new TipTracker(8);
        Tip tip = new Tip("tip1", new Coordinate(0, 0), Volume.ul(300));
        
        TipTracker afterPickup = tracker.withTipPickedUp(0, tip);
        assertFalse(afterPickup.getTip(0).isEmpty());
        assertEquals(tip, afterPickup.getTip(0).tip());
        
        TipTracker afterDrop = afterPickup.withTipDropped(0);
        assertTrue(afterDrop.getTip(0).isEmpty());
    }
    
    @Test
    void testCannotPickupTwice() {
        TipTracker tracker = new TipTracker(8);
        Tip tip1 = new Tip("tip1", new Coordinate(0, 0), Volume.ul(300));
        Tip tip2 = new Tip("tip2", new Coordinate(0, 0), Volume.ul(300));
        
        TipTracker afterPickup = tracker.withTipPickedUp(0, tip1);
        
        assertThrows(IllegalStateException.class, () -> {
            afterPickup.withTipPickedUp(0, tip2);
        });
    }
}
