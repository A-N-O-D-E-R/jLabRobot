# Scale Module

Digital scale/balance for mass measurement.

## Components

- `Scale` - Main API
- `ScaleBackend` - Hardware interface

## Usage

```java
ScaleBackend backend = new MyScaleBackend();
Scale scale = new Scale(backend);

scale.setup();

// Tare
scale.tare();

// Read weight
double weight = scale.readWeight(); // grams

scale.stop();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
