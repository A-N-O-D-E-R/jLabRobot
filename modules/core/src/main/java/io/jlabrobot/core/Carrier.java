package io.jlabrobot.core;

import java.util.List;

public interface Carrier extends Resource {
    List<Resource> getChildren();
    void addChild(Resource child);
    void removeChild(Resource child);
}
