package io.jlabrobot.liquidhandling;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines channel access patterns for 96-well plates.
 * Supports full plate access or selective row/column patterns.
 */
public enum ChannelPattern {
    ALL_96,
    COLUMN_1,
    COLUMN_2,
    COLUMN_3,
    COLUMN_4,
    COLUMN_5,
    COLUMN_6,
    COLUMN_7,
    COLUMN_8,
    COLUMN_9,
    COLUMN_10,
    COLUMN_11,
    COLUMN_12,
    ROW_A,
    ROW_B,
    ROW_C,
    ROW_D,
    ROW_E,
    ROW_F,
    ROW_G,
    ROW_H;

    /**
     * Gets the list of channel indices for this pattern.
     * @return the channel indices matching this pattern
     * @throws IllegalStateException if pattern is unknown
     */
    public List<Integer> getChannels() {
        if (this == ALL_96) {
            List<Integer> all = new ArrayList<>(96);
            for (int i = 0; i < 96; i++) {
                all.add(i);
            }
            return all;
        }

        if (name().startsWith("COLUMN_")) {
            int col = Integer.parseInt(name().substring(7)) - 1;
            List<Integer> channels = new ArrayList<>(8);
            for (int row = 0; row < 8; row++) {
                channels.add(row * 12 + col);
            }
            return channels;
        }

        if (name().startsWith("ROW_")) {
            int row = name().charAt(4) - 'A';
            List<Integer> channels = new ArrayList<>(12);
            for (int col = 0; col < 12; col++) {
                channels.add(row * 12 + col);
            }
            return channels;
        }

        throw new IllegalStateException("Unknown pattern: " + this);
    }

    /**
     * Creates a column pattern for the specified column number.
     * @param col the column number (1-12)
     * @return the column pattern
     * @throws IllegalArgumentException if column is not 1-12
     */
    public static ChannelPattern column(int col) {
        if (col < 1 || col > 12) {
            throw new IllegalArgumentException("Column must be 1-12");
        }
        return valueOf("COLUMN_" + col);
    }

    /**
     * Creates a row pattern for the specified row letter.
     * @param row the row letter (A-H)
     * @return the row pattern
     * @throws IllegalArgumentException if row is not A-H
     */
    public static ChannelPattern row(char row) {
        if (row < 'A' || row > 'H') {
            throw new IllegalArgumentException("Row must be A-H");
        }
        return valueOf("ROW_" + row);
    }
}
