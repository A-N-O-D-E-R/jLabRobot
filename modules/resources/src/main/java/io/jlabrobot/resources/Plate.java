package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractCarrier;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.ItemizedResource;
import io.jlabrobot.core.Resource;
import io.jlabrobot.core.Volume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plate extends AbstractCarrier implements ItemizedResource {
    private final int numRows;
    private final int numColumns;
    private final Well[][] wells;

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

    public static Plate createPlate96(String name) {
        return new Plate(name, 8, 12, 9.0, new Volume(200));
    }

    public static Plate createPlate384(String name) {
        return new Plate(name, 16, 24, 4.5, new Volume(100));
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public Well getItem(int row, int column) {
        if (row < 0 || row >= numRows || column < 0 || column >= numColumns) {
            throw new IndexOutOfBoundsException("Invalid well position: [" + row + "," + column + "]");
        }
        return wells[row][column];
    }

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
