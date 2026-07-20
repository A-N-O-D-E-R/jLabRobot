# Backend API Module

Strategy pattern interface for pluggable hardware backends.

## Interface

```java
public interface Backend {
    void initialize() throws BackendException;
    CommandResult executeCommand(Command cmd) throws BackendException;
    void shutdown();
}
```

## Value Objects

**Command** (record)
```java
Command cmd = new Command("aspirate", Map.of(
    "volume", 50.0,
    "well", wellPosition
));
```

**CommandResult** (record)
```java
CommandResult.success("OK");
CommandResult.success("OK", dataObject);
CommandResult.failure("Error message");
```

**BackendException** (exception)
- Wraps hardware communication errors
- Thrown by `initialize()` and `executeCommand()`

## Implementations

| Backend | Hardware | Protocol |
|---------|----------|----------|
| `HamiltonBackend` | Hamilton STAR/Vantage | Text (Serial/TCP) |
| `TecanBackend` | Tecan EVO | Binary (USB) |
| `OpentronsBackend` | Opentrons OT-2/Flex | Python script gen |
| `ChatterboxBackend` | Mock (no hardware) | In-memory |

## Usage

```java
// Select backend
Backend backend = new HamiltonBackend(
    new SerialChannel(),
    "/dev/ttyUSB0"
);

// Inject into LiquidHandler
LiquidHandler lh = new LiquidHandler(deck, backend);

lh.initialize();  // → backend.initialize()
lh.aspirate(...); // → backend.executeCommand(aspirateCommand)
lh.shutdown();    // → backend.shutdown()
```

## Creating Custom Backend

```java
public class CustomBackend implements Backend {
    @Override
    public void initialize() throws BackendException {
        // Connect to hardware
    }

    @Override
    public CommandResult executeCommand(Command cmd) throws BackendException {
        // Translate command to hardware protocol
        switch (cmd.name()) {
            case "aspirate" -> sendAspirateCommand(cmd.parameters());
            case "dispense" -> sendDispenseCommand(cmd.parameters());
            // ...
        }
        return CommandResult.success("OK");
    }

    @Override
    public void shutdown() {
        // Disconnect
    }
}
```

## Design Pattern

**Strategy Pattern**: Decouples liquid handling logic from hardware communication.
- LiquidHandler knows *what* to do
- Backend knows *how* to communicate with hardware

## Dependencies

None - pure Java 17
