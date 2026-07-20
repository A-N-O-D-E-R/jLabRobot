# Visualization Module

Deck layout rendering for debugging and protocol development.

## Component

**AsciiDeckRenderer** - Renders deck layouts as ASCII art

## Usage

```java
Deck deck = new Deck("MainDeck");

Plate plate = Plate.createPlate96("plate");
deck.addChild(plate);

// Render with default size (600mm × 400mm)
String layout = AsciiDeckRenderer.render(deck);
System.out.println(layout);

// Custom size
String layout = AsciiDeckRenderer.render(deck, 150, 100); // width, height in mm
```

## Output

```
..............................
..............................
..............................
oo.o.o.o.oo.o.o.o.oo..........
..............................
oo.o.o.o.oo.o.o.o.oo..........
oo.o.o.o.oo.o.o.o.oo..........
..............................
oo.o.o.o.oo.o.o.o.oo..........
```

**Symbols**:
- `.` = Empty deck space
- `#` = Carrier (deck, plate, tip rack)
- `o` = Well or tip

## Resolution

- Default: **5mm per character**
- Adjustable via size parameters
- 96-well plate (108mm × 72mm) = ~22 × 14 chars

## Hierarchical Rendering

Renders entire resource tree:
```
Deck
  ├─ TipRack → shows tips as 'o'
  └─ Plate → shows wells as 'o'
```

## Use Cases

- Debug deck layouts
- Verify resource positions
- Visualize multi-plate setups
- Documentation/screenshots

## Limitations

- ASCII only (no SVG/graphics)
- Fixed symbols (no colors)
- 2D projection (no Z-axis)
- Overlapping resources show single char

## Future Enhancements

- SVG renderer
- Color-coded resources
- Interactive deck builder
- 3D visualization

## Dependencies

- `jlabrobot-core` - Resource, Coordinate, Carrier
- `jlabrobot-resources` - Deck, Plate
