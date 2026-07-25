package io.jlabrobot.core;

/**
 * Abstract base class for implementing the Resource interface.
 * Provides default implementations for managing resource name, location, and parent relationships.
 */
public abstract class AbstractResource implements Resource {
    private final String name;
    private final Coordinate location;
    private Resource parent;

    /**
     * Constructs an AbstractResource with a name and relative location.
     *
     * @param name the name of the resource
     * @param location the location of the resource relative to its parent
     */
    protected AbstractResource(String name, Coordinate location) {
        this.name = name;
        this.location = location;
    }

    /**
     * Returns the name of this resource.
     *
     * @return the resource name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the location of this resource relative to its parent.
     *
     * @return the relative coordinate
     */
    @Override
    public Coordinate getLocation() {
        return location;
    }

    /**
     * Returns the parent resource containing this resource, or null if this is a root resource.
     *
     * @return the parent resource, or null if this is a root resource
     */
    @Override
    public Resource getParent() {
        return parent;
    }

    /**
     * Sets the parent resource for this resource.
     *
     * @param parent the parent resource, or null to make this a root resource
     */
    @Override
    public void setParent(Resource parent) {
        this.parent = parent;
    }
}
