package io.jlabrobot.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCarrier extends AbstractResource implements Carrier {
    private final List<Resource> children = new ArrayList<>();
    
    protected AbstractCarrier(String name, Coordinate location) {
        super(name, location);
    }
    
    @Override
    public List<Resource> getChildren() {
        return Collections.unmodifiableList(children);
    }
    
    @Override
    public void addChild(Resource child) {
        children.add(child);
        child.setParent(this);
    }
    
    @Override
    public void removeChild(Resource child) {
        children.remove(child);
        child.setParent(null);
    }
}
