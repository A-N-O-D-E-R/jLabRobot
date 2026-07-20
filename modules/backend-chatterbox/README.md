# Chatterbox Backend

Mock backend for testing without physical hardware.

## Purpose

In-memory backend that simulates hardware responses. Useful for:
- Unit testing liquid handling logic
- Protocol development without hardware
- CI/CD pipelines
- Example programs

## Usage

```java
Backend backend = new ChatterboxBackend();
LiquidHandler lh = new LiquidHandler(deck, backend);

lh.initialize();  // No hardware required
lh.pickUpTips(tips);
lh.aspirate(wells, volumes);
lh.dispense(wells, volumes);
lh.dropTips(tips);
lh.shutdown();

// All commands succeed immediately
```

## Behavior

- `initialize()`: Returns immediately
- `executeCommand()`: Logs command name, returns success
- `shutdown()`: Returns immediately
- No validation of coordinates, volumes, or deck state
- No simulation of timing or hardware constraints

## Example Output

```
Chatterbox backend initialized
Chatterbox executing: pick_up_tips
Chatterbox executing: aspirate
Chatterbox executing: dispense
Chatterbox executing: drop_tips
Chatterbox backend shutdown
```

## Use in Examples

```java
public class MyExample {
    public static void main(String[] args) throws Exception {
        // Use Chatterbox for demo without hardware
        Backend backend = new ChatterboxBackend();
        LiquidHandler lh = new LiquidHandler(deck, backend);

        // ... protocol steps ...
    }
}
```

## Not Suitable For

- Hardware validation
- Timing-sensitive operations
- Error condition testing
- Volume balance verification

Use hardware integration tests for validation (see `integration-tests` module).

## Dependencies

- `jlabrobot-backend-api`
