package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;

/**
 * Represents the state of a single channel/tip on a pipette head.
 * Records whether a tip is present and mounted.
 */
public record TipSpot(Tip tip, boolean mounted) {
    /**
     * Creates an empty TipSpot representing an unmounted channel.
     * @return an empty TipSpot
     */
    public static TipSpot empty() {
        return new TipSpot(null, false);
    }

    /**
     * Checks whether this tip spot is empty.
     * @return true if no tip is present, false otherwise
     */
    public boolean isEmpty() {
        return tip == null;
    }
}
