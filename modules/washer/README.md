# Washer Module

Microplate washer for cell culture and immunoassays.

## Components

- `Washer` - Main API
- `WasherBackend` - Hardware interface

## Usage

```java
WasherBackend backend = new MyWasherBackend();
Washer washer = new Washer(backend);

washer.setup();

// Wash cycle
washer.wash(
    3,      // Number of washes
    200.0,  // Volume per wash (µL)
    30      // Soak time (seconds)
);

// Aspirate only
washer.aspirate();

washer.stop();
```

## Status

Stub implementation - needs hardware-specific backend.

## Dependencies

- `jlabrobot-machines` - Machine interface
