# Centrifuge Module

Centrifuge equipment for sample separation.

## Components

- `Centrifuge` - Main API  
- `CentrifugeBackend` - Hardware interface

## Usage

```java
CentrifugeBackend backend = new MyCentrifugeBackend();
Centrifuge centrifuge = new Centrifuge(backend);

centrifuge.setup();

// Spin
centrifuge.spin(3000, 60); // 3000 RPM for 60 seconds

// Wait for completion
centrifuge.waitForCompletion();

centrifuge.stop();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
