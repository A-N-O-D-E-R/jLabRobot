package io.jlabrobot.core;

public abstract class AbstractResource implements Resource {
    private final String name;
    private final Coordinate location;
    private Resource parent;
    
    protected AbstractResource(String name, Coordinate location) {
        this.name = name;
        this.location = location;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Coordinate getLocation() {
        return location;
    }
    
    @Override
    public Resource getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Resource parent) {
        this.parent = parent;
    }
}
