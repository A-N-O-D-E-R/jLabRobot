# Heating-Shaking Module

Heater-shaker equipment for temperature control and agitation.

## Components

- `HeaterShaker` - Main API
- `HeaterShakerBackend` - Hardware interface

## Usage

```java
HeaterShakerBackend backend = new HamiltonHeaterShakerBackend();
HeaterShaker shaker = new HeaterShaker(backend);

shaker.setup();

// Set temperature
shaker.setTemperature(37.0); // Celsius

// Set shaking
shaker.setShakeSpeed(300); // RPM

// Wait for conditions
shaker.waitForTemperature();

// Stop
shaker.setShakeSpeed(0);
shaker.stop();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
