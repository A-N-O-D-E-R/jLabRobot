# Resources Module

Concrete implementations of lab equipment resources.

## Labware

**Plates**
- `Plate` - Microplates with well grids
  - `createPlate96()` - 8×12 wells, 9mm spacing, 200µL
  - `createPlate384()` - 16×24 wells, 4.5mm spacing, 100µL
- `Well` - Individual plate well with volume tracking

**Tips**
- `TipRack` - Tip holder with usage tracking
  - `createTipRack96()` - 8×12 tips, 9mm spacing, 300µL
- `Tip` - Pipette tip with used/unused state

**Containers**
- `Deck` - Top-level carrier (deck surface)
- Custom carriers via `AbstractCarrier`

## Catalogs

**Vendor Definitions**
- `ResourceCatalog` - Labware library interface
- `Corning` - Corning labware catalog
- `Falcon` - Falcon labware catalog

**Plate Definitions**
- `PlateDefinition` - JSON-based plate specifications

## Usage

```java
// Create deck
Deck deck = new Deck("MainDeck");

// Add labware
TipRack tips = TipRack.createTipRack96("tips");
Plate plate = Plate.createPlate96("plate1");
deck.addChild(tips);
deck.addChild(plate);

// Access wells
Well wellA1 = plate.getItem(0, 0);  // Row 0, Col 0
Well wellB2 = plate.getItem(1, 1);  // Row 1, Col 1

// Volume tracking
wellA1.setCurrentVolume(new Volume(100));
System.out.println(wellA1.getCurrentVolume().microliters()); // 100.0
```

## Well Naming

- **Software**: 0-indexed (row 0 = A, col 0 = 1)
- **Human labels**: A1, B2, H12
- Use `getItem(row, col)` for programmatic access

## Dependencies

- `jlabrobot-core` - Resource interfaces, Coordinate, Volume
