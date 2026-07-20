# Pump Module

Peristaltic and syringe pump control.

## Components

- `Pump` - Main API
- `PumpBackend` - Hardware interface

**Implementations**
- `MasterflexBackend` - Masterflex peristaltic pumps (stub)

## Usage

```java
PumpBackend backend = new MasterflexBackend();
Pump pump = new Pump(backend);

pump.setup();

// Dispense volume
pump.dispense(10.0); // mL

// Continuous flow
pump.setFlowRate(5.0); // mL/min
pump.start();
Thread.sleep(60000); // Run for 1 minute
pump.stop();

pump.shutdown();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
