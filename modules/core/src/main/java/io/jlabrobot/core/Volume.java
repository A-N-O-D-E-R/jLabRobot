package io.jlabrobot.core;

public record Volume(double microliters) implements Comparable<Volume> {
    public Volume {
        if (microliters < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
    }
    
    public static Volume ul(double microliters) {
        return new Volume(microliters);
    }
    
    public static Volume ml(double milliliters) {
        return new Volume(milliliters * 1000.0);
    }
    
    public Volume add(Volume other) {
        return new Volume(microliters + other.microliters);
    }
    
    public Volume subtract(Volume other) {
        return new Volume(microliters - other.microliters);
    }
    
    @Override
    public int compareTo(Volume other) {
        return Double.compare(microliters, other.microliters);
    }
}
