# Incubator Module

Temperature and CO₂ controlled incubator.

## Components

- `Incubator` - Main API
- `IncubatorBackend` - Hardware interface

## Usage

```java
IncubatorBackend backend = new MyIncubatorBackend();
Incubator incubator = new Incubator(backend);

incubator.setup();

// Set conditions
incubator.setTemperature(37.0);  // Celsius
incubator.setCO2(5.0);           // Percent

// Wait for stability
incubator.waitForConditions();

// Long incubation
Thread.sleep(24 * 3600 * 1000); // 24 hours

incubator.stop();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
