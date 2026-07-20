package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractCarrier;
import io.jlabrobot.core.Coordinate;

public class Deck extends AbstractCarrier {
    public Deck(String name) {
        super(name, new Coordinate(0, 0, 0));
    }
}
