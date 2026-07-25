package io.jlabrobot.core;

/**
 * Represents a physical resource in the laboratory automation system.
 * Resources can be nested hierarchically, with each resource having a location relative to its parent.
 */
public interface Resource {
    /**
     * Returns the name of this resource.
     *
     * @return the resource name
     */
    String getName();

    /**
     * Returns the location of this resource relative to its parent.
     *
     * @return the relative coordinate
     */
    Coordinate getLocation();

    /**
     * Returns the parent resource containing this resource, or null if this is a root resource.
     *
     * @return the parent resource, or null if this is a root resource
     */
    Resource getParent();

    /**
     * Sets the parent resource for this resource.
     *
     * @param parent the parent resource, or null to make this a root resource
     */
    void setParent(Resource parent);

    /**
     * Returns the absolute location of this resource in the coordinate system of the root resource.
     * Calculated by recursively adding the location of each parent resource.
     *
     * @return the absolute coordinate
     */
    default Coordinate getAbsoluteLocation() {
        if (getParent() == null) {
            return getLocation();
        }
        return getParent().getAbsoluteLocation().add(getLocation());
    }
}
