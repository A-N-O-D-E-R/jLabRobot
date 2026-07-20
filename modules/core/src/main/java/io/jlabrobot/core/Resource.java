package io.jlabrobot.core;

public interface Resource {
    String getName();
    Coordinate getLocation();
    Resource getParent();
    void setParent(Resource parent);
    
    default Coordinate getAbsoluteLocation() {
        if (getParent() == null) {
            return getLocation();
        }
        return getParent().getAbsoluteLocation().add(getLocation());
    }
}
