package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;

public record TipSpot(Tip tip, boolean mounted) {
    public static TipSpot empty() {
        return new TipSpot(null, false);
    }
    
    public boolean isEmpty() {
        return tip == null;
    }
}
