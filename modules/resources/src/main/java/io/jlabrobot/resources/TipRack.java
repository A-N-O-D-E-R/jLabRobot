package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractCarrier;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.ItemizedResource;
import io.jlabrobot.core.Volume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TipRack extends AbstractCarrier implements ItemizedResource {
    private final int numRows;
    private final int numColumns;
    private final Tip[][] tips;

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

    public static TipRack createTipRack96(String name) {
        return new TipRack(name, 8, 12, 9.0, new Volume(300));
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public Tip getItem(int row, int column) {
        if (row < 0 || row >= numRows || column < 0 || column >= numColumns) {
            throw new IndexOutOfBoundsException("Invalid tip position: [" + row + "," + column + "]");
        }
        return tips[row][column];
    }

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
