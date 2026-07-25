package io.jlabrobot.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for implementing the Carrier interface.
 * Provides default implementations for managing child resources and maintaining parent-child relationships.
 */
public abstract class AbstractCarrier extends AbstractResource implements Carrier {
    private final List<Resource> children = new ArrayList<>();

    /**
     * Constructs an AbstractCarrier with a name and relative location.
     *
     * @param name the name of the carrier
     * @param location the location of the carrier relative to its parent
     */
    protected AbstractCarrier(String name, Coordinate location) {
        super(name, location);
    }

    /**
     * Returns an unmodifiable list of all child resources contained by this carrier.
     *
     * @return an unmodifiable list of child resources
     */
    @Override
    public List<Resource> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Adds a child resource to this carrier and sets this carrier as the child's parent.
     *
     * @param child the resource to add as a child
     */
    @Override
    public void addChild(Resource child) {
        children.add(child);
        child.setParent(this);
    }

    /**
     * Removes a child resource from this carrier and clears its parent relationship.
     *
     * @param child the resource to remove from this carrier
     */
    @Override
    public void removeChild(Resource child) {
        children.remove(child);
        child.setParent(null);
    }
}
