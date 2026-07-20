package io.jlabrobot.liquidhandling;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChannelPatternTest {

    @Test
    void testAll96() {
        List<Integer> channels = ChannelPattern.ALL_96.getChannels();
        assertEquals(96, channels.size());
        assertEquals(0, channels.get(0));
        assertEquals(95, channels.get(95));
    }

    @Test
    void testColumn1() {
        List<Integer> channels = ChannelPattern.COLUMN_1.getChannels();
        assertEquals(8, channels.size());
        assertEquals(List.of(0, 12, 24, 36, 48, 60, 72, 84), channels);
    }

    @Test
    void testColumn12() {
        List<Integer> channels = ChannelPattern.COLUMN_12.getChannels();
        assertEquals(8, channels.size());
        assertEquals(List.of(11, 23, 35, 47, 59, 71, 83, 95), channels);
    }

    @Test
    void testRowA() {
        List<Integer> channels = ChannelPattern.ROW_A.getChannels();
        assertEquals(12, channels.size());
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), channels);
    }

    @Test
    void testRowH() {
        List<Integer> channels = ChannelPattern.ROW_H.getChannels();
        assertEquals(12, channels.size());
        assertEquals(List.of(84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95), channels);
    }

    @Test
    void testColumnFactory() {
        List<Integer> channels = ChannelPattern.column(6).getChannels();
        assertEquals(8, channels.size());
        assertEquals(List.of(5, 17, 29, 41, 53, 65, 77, 89), channels);
    }

    @Test
    void testRowFactory() {
        List<Integer> channels = ChannelPattern.row('D').getChannels();
        assertEquals(12, channels.size());
        assertEquals(List.of(36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47), channels);
    }

    @Test
    void testInvalidColumn() {
        assertThrows(IllegalArgumentException.class, () -> ChannelPattern.column(0));
        assertThrows(IllegalArgumentException.class, () -> ChannelPattern.column(13));
    }

    @Test
    void testInvalidRow() {
        assertThrows(IllegalArgumentException.class, () -> ChannelPattern.row('I'));
        assertThrows(IllegalArgumentException.class, () -> ChannelPattern.row('@'));
    }
}
