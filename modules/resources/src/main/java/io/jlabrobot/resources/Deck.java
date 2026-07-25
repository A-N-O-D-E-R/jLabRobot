package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractCarrier;
import io.jlabrobot.core.Coordinate;

/**
 * Represents the deck of a laboratory automation robot.
 *
 * A Deck is a carrier resource that serves as the top-level container for all
 * equipment and resources placed on a robot's work surface. It holds plates,
 * tip racks, and other labware in specific positions (slots) where the robot
 * can access them. The deck provides a 3D coordinate system for positioning resources.
 */
public class Deck extends AbstractCarrier {
    /**
     * Creates a new deck with the specified name.
     *
     * The deck is initialized at the origin (0, 0, 0) as the root container
     * for all other resources.
     *
     * @param name the unique name identifier for this deck
     */
    public Deck(String name) {
        super(name, new Coordinate(0, 0, 0));
    }
}
