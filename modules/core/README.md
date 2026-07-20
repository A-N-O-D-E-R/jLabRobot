# Core Module

Base abstractions for jLabRobot lab automation framework.

## Contents

**Interfaces**
- `Resource` - Base interface for all lab equipment (plates, tips, carriers)
- `Carrier` - Resources that contain other resources (deck, plate carriers)
- `Container` - Resources that hold liquid (wells, tubes)
- `ItemizedResource` - Resources with indexed items (plates with wells)

**Value Objects**
- `Coordinate` - 3D position (x, y, z in millimeters)
- `Volume` - Liquid volume (microliters)

**Base Classes**
- `AbstractResource` - Common resource implementation
- `AbstractCarrier` - Parent-child hierarchy support

## Design Pattern

**Composite Pattern** - Resources form a tree hierarchy:
```
Deck (Carrier)
  ├─ TipRack (Carrier + ItemizedResource)
  │    └─ Tip (Resource)
  └─ Plate (Carrier + ItemizedResource)
       └─ Well (Container)
```

## Usage

```java
// Create resource
Plate plate = new Plate("source", 8, 12, 9.0, new Volume(200));

// Set parent relationship
deck.addChild(plate);

// Get absolute coordinates (resolves parent chain)
Coordinate abs = plate.getAbsoluteLocation(); // deck.location + plate.location
```

## Coordinate System

- **Right-handed Cartesian**: X right, Y back, Z up
- **Units**: Millimeters
- **Relative positioning**: Coordinates relative to parent resource
- **Absolute**: Calculated via `getAbsoluteLocation()` traversing parent chain

See `/COORDINATES.md` for detailed coordinate system guide.

## Dependencies

None - pure Java 17 (records, sealed interfaces)
