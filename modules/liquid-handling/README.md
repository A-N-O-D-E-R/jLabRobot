# Liquid Handling Module

High-level API for liquid handling operations.

## Components

**Main APIs**
- `LiquidHandler` - Single-channel pipetting operations
- `LiquidHandler96` - 96-channel head operations

**State Tracking**
- `Head` - Single-channel pipette state
- `Head96` - 96-channel head state
- `VolumeTracker` - Aspirate/dispense volume tracking
- `TipTracker` - Tip usage tracking

**Channel Management**
- `ChannelPattern` - Channel selection patterns (rows, columns, custom)
- `TipSpot` - Individual tip position

**Liquid Classes**
- `LiquidClass` - Liquid handling parameters
- `AspirateParameters` - Aspiration settings
- `DispenseParameters` - Dispense settings

## Single-Channel Operations

```java
Backend backend = new HamiltonBackend(channel, "COM3");
LiquidHandler lh = new LiquidHandler(deck, backend);

lh.initialize();

// Pick up tips
List<Tip> tips = List.of(tipRack.getItem(0, 0));
lh.pickUpTips(tips);

// Aspirate
lh.aspirate(List.of(sourceWell), List.of(50.0));  // 50µL

// Dispense
lh.dispense(List.of(destWell), List.of(50.0));

// Drop tips
lh.dropTips(tips);

lh.shutdown();
```

## 96-Channel Operations

```java
LiquidHandler96 lh96 = new LiquidHandler96(deck, backend);

lh96.initialize();

// Full plate operations
lh96.pickUpTips96(tipRack);
lh96.aspirate96(sourcePlate, new Volume(50));
lh96.dispense96(destPlate, new Volume(50));
lh96.dropTips96();

// Column operations
lh96.aspirateColumn(plate, 0, new Volume(100));  // Column 1
lh96.dispenseColumn(plate, 1, new Volume(100));  // Column 2

// Row operations
lh96.aspirateRow(plate, 'A', new Volume(50));  // Row A
lh96.dispenseRow(plate, 'B', new Volume(50));  // Row B

// Convenience
lh96.transfer96(sourcePlate, destPlate, new Volume(50));

lh96.shutdown();
```

## Volume Tracking

Automatic volume tracking per well:
```java
sourceWell.setCurrentVolume(new Volume(100));
lh.aspirate(List.of(sourceWell), List.of(50.0));
// sourceWell now has 50µL

lh.dispense(List.of(destWell), List.of(50.0));
// destWell now has 50µL
```

## Backend Integration

LiquidHandler translates high-level operations to backend commands:
```java
lh.aspirate(wells, volumes);
// → backend.executeCommand(new Command("aspirate", params))
```

Backend implementations:
- `HamiltonBackend` - Hamilton STAR/Vantage
- `TecanBackend` - Tecan EVO
- `OpentronsBackend` - Opentrons OT-2/Flex
- `ChatterboxBackend` - Mock for testing

## Dependencies

- `jlabrobot-core` - Resource, Coordinate, Volume
- `jlabrobot-resources` - Deck, Plate, Well, Tip
- `jlabrobot-backend-api` - Backend interface
