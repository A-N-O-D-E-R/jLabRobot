# Thermocycler Module

PCR thermocycler control for temperature cycling.

## Components

- `Thermocycler` - Main API
- `ThermocyclerBackend` - Hardware interface

## Usage

```java
ThermocyclerBackend backend = new MyThermocyclerBackend();
Thermocycler tc = new Thermocycler(backend);

tc.setup();

// Simple cycle
tc.setLidTemperature(105.0);
tc.setBlockTemperature(95.0);
tc.hold(300); // 5 minutes

// PCR cycles
for (int i = 0; i < 30; i++) {
    tc.setBlockTemperature(95.0);
    tc.hold(30); // Denature
    
    tc.setBlockTemperature(55.0);
    tc.hold(30); // Anneal
    
    tc.setBlockTemperature(72.0);
    tc.hold(60); // Extend
}

tc.stop();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
