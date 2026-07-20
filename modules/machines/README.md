# Machines Module

Generic machine abstraction for lab equipment that doesn't fit liquid handling.

## Purpose

Base classes for equipment modules:
- Heater-shakers
- Centrifuges
- Scales
- Pumps
- Thermocyclers
- Incubators
- Washers

## Components

**Machine** - Generic equipment interface
```java
public interface Machine {
    void setup() throws Exception;
    void stop();
}
```

**MachineBackend** - Hardware abstraction
```java
public interface MachineBackend {
    void initialize() throws Exception;
    void shutdown();
}
```

## Usage Pattern

```java
// Equipment-specific interface extends Machine
public class HeaterShaker implements Machine {
    private final HeaterShakerBackend backend;

    public void setTemperature(double celsius) { ... }
    public void setShakeSpeed(int rpm) { ... }
}

// Backend implements hardware communication
public class HamiltonHeaterShakerBackend implements HeaterShakerBackend {
    @Override
    public void setTemperature(double celsius) {
        // Send command to hardware
    }
}
```

## Design Pattern

Same strategy pattern as liquid handling:
- **Machine**: High-level operations
- **Backend**: Hardware communication
- Pluggable backends for different manufacturers

## Status

Generic abstractions defined. Equipment-specific implementations in:
- `heating-shaking`
- `centrifuge`
- `scale`
- `pump`
- `thermocycler`
- `incubator`
- `washer`

## Dependencies

None - pure interfaces
