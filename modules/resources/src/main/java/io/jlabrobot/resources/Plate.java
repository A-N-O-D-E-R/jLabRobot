package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractCarrier;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.ItemizedResource;
import io.jlabrobot.core.Resource;
import io.jlabrobot.core.Volume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a microplate resource with a grid of wells.
 *
 * A Plate is a standard laboratory container consisting of a fixed number of wells
 * arranged in rows and columns. Wells are identified by their row and column indices,
 * and can also be referenced by standard naming conventions (e.g., A1, A2, B1).
 *
 * Common plate formats include 96-well (8 rows x 12 columns) and 384-well (16 rows x 24 columns) plates.
 * Each well is a liquid container with a maximum volume capacity.
 */
public class Plate extends AbstractCarrier implements ItemizedResource {
    private final int numRows;
    private final int numColumns;
    private final Well[][] wells;

    /**
     * Creates a new plate with the specified dimensions and well properties.
     *
     * @param name the unique name identifier for this plate instance
     * @param numRows the number of rows in the plate grid
     * @param numColumns the number of columns in the plate grid
     * @param wellSpacing the spacing between well centers in millimeters
     * @param wellVolume the maximum volume capacity of each well
     */
    public Plate(String name, int numRows, int numColumns, double wellSpacing, Volume wellVolume) {
        super(name, new Coordinate(0, 0));
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.wells = new Well[numRows][numColumns];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                String wellName = name + "_" + rowToLetter(row) + (col + 1);
                Coordinate wellLocation = new Coordinate(col * wellSpacing, row * wellSpacing);
                Well well = new Well(wellName, wellLocation, wellVolume);
                wells[row][col] = well;
                addChild(well);
            }
        }
    }

    /**
     * Creates a standard 96-well plate with 8 rows and 12 columns.
     *
     * Well format: 8 rows x 12 columns, 200 µL capacity per well, 9mm spacing.
     *
     * @param name the unique name identifier for this plate instance
     * @return a new 96-well plate with standard dimensions
     */
    public static Plate createPlate96(String name) {
        return new Plate(name, 8, 12, 9.0, new Volume(200));
    }

    /**
     * Creates a standard 384-well plate with 16 rows and 24 columns.
     *
     * Well format: 16 rows x 24 columns, 100 µL capacity per well, 4.5mm spacing.
     *
     * @param name the unique name identifier for this plate instance
     * @return a new 384-well plate with standard dimensions
     */
    public static Plate createPlate384(String name) {
        return new Plate(name, 16, 24, 4.5, new Volume(100));
    }

    /**
     * Returns the number of rows in this plate.
     *
     * @return the row count (typically 8 for 96-well or 16 for 384-well plates)
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in this plate.
     *
     * @return the column count (typically 12 for 96-well or 24 for 384-well plates)
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Retrieves the well at the specified row and column position.
     *
     * @param row the zero-based row index
     * @param column the zero-based column index
     * @return the well at the specified position
     * @throws IndexOutOfBoundsException if the row or column index is out of bounds
     */
    public Well getItem(int row, int column) {
        if (row < 0 || row >= numRows || column < 0 || column >= numColumns) {
            throw new IndexOutOfBoundsException("Invalid well position: [" + row + "," + column + "]");
        }
        return wells[row][column];
    }

    /**
     * Returns an unmodifiable list of all wells in this plate.
     *
     * Wells are returned in row-major order (all columns of row 0, then row 1, etc.).
     *
     * @return an immutable list containing all wells in this plate
     */
    public List<Well> getAllItems() {
        List<Well> allWells = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                allWells.add(wells[row][col]);
            }
        }
        return Collections.unmodifiableList(allWells);
    }

    private String rowToLetter(int row) {
        return String.valueOf((char) ('A' + row));
    }
}
