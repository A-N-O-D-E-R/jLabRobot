package io.jlabrobot.liquidhandling;

import java.util.ArrayList;
import java.util.List;

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

    public static ChannelPattern column(int col) {
        if (col < 1 || col > 12) {
            throw new IllegalArgumentException("Column must be 1-12");
        }
        return valueOf("COLUMN_" + col);
    }

    public static ChannelPattern row(char row) {
        if (row < 'A' || row > 'H') {
            throw new IllegalArgumentException("Row must be A-H");
        }
        return valueOf("ROW_" + row);
    }
}
