package io.jlabrobot.core;

import java.util.List;

/**
 * Represents a resource that can contain other resources as children.
 * A carrier can manage a hierarchical structure of resources.
 */
public interface Carrier extends Resource {
    /**
     * Returns a list of all child resources contained by this carrier.
     *
     * @return an unmodifiable list of child resources
     */
    List<Resource> getChildren();

    /**
     * Adds a child resource to this carrier and sets this carrier as the child's parent.
     *
     * @param child the resource to add as a child
     */
    void addChild(Resource child);

    /**
     * Removes a child resource from this carrier and clears the parent relationship.
     *
     * @param child the resource to remove from this carrier
     */
    void removeChild(Resource child);
}
