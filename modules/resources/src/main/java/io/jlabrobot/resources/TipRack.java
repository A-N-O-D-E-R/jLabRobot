package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractCarrier;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.ItemizedResource;
import io.jlabrobot.core.Volume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a rack of pipette tips for liquid handling operations.
 *
 * A TipRack is a physical container that holds pipette tips in a grid arrangement.
 * Tips are consumable resources that are typically used once and then discarded.
 * Each tip tracks its usage state (used/unused) and has a maximum volume capacity.
 *
 * Standard tip rack formats include 96-tip racks (8 rows x 12 columns) to match
 * standard plate layouts, allowing parallel tip pickup by multi-channel pipettes.
 */
public class TipRack extends AbstractCarrier implements ItemizedResource {
    private final int numRows;
    private final int numColumns;
    private final Tip[][] tips;

    /**
     * Creates a new tip rack with the specified dimensions and tip properties.
     *
     * @param name the unique name identifier for this tip rack instance
     * @param numRows the number of rows in the tip rack grid
     * @param numColumns the number of columns in the tip rack grid
     * @param tipSpacing the spacing between tip centers in millimeters
     * @param tipVolume the maximum volume capacity of each tip
     */
    public TipRack(String name, int numRows, int numColumns, double tipSpacing, Volume tipVolume) {
        super(name, new Coordinate(0, 0));
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.tips = new Tip[numRows][numColumns];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                String tipName = name + "_" + row + "_" + col;
                Coordinate tipLocation = new Coordinate(col * tipSpacing, row * tipSpacing);
                Tip tip = new Tip(tipName, tipLocation, tipVolume);
                tips[row][col] = tip;
                addChild(tip);
            }
        }
    }

    /**
     * Creates a standard 96-tip rack with 8 rows and 12 columns.
     *
     * Tip format: 8 rows x 12 columns, 300 µL capacity per tip, 9mm spacing.
     * This layout matches the standard 96-well plate format for parallel liquid handling.
     *
     * @param name the unique name identifier for this tip rack instance
     * @return a new 96-tip rack with standard dimensions
     */
    public static TipRack createTipRack96(String name) {
        return new TipRack(name, 8, 12, 9.0, new Volume(300));
    }

    /**
     * Returns the number of rows in this tip rack.
     *
     * @return the row count (typically 8 for standard 96-tip racks)
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in this tip rack.
     *
     * @return the column count (typically 12 for standard 96-tip racks)
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Retrieves the tip at the specified row and column position.
     *
     * @param row the zero-based row index
     * @param column the zero-based column index
     * @return the tip at the specified position
     * @throws IndexOutOfBoundsException if the row or column index is out of bounds
     */
    public Tip getItem(int row, int column) {
        if (row < 0 || row >= numRows || column < 0 || column >= numColumns) {
            throw new IndexOutOfBoundsException("Invalid tip position: [" + row + "," + column + "]");
        }
        return tips[row][column];
    }

    /**
     * Returns an unmodifiable list of all tips in this rack.
     *
     * Tips are returned in row-major order (all columns of row 0, then row 1, etc.).
     *
     * @return an immutable list containing all tips in this rack
     */
    public List<Tip> getAllItems() {
        List<Tip> allTips = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                allTips.add(tips[row][col]);
            }
        }
        return Collections.unmodifiableList(allTips);
    }
}
