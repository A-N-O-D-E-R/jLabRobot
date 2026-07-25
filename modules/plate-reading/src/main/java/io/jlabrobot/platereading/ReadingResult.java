package io.jlabrobot.platereading;

import java.util.Map;

/**
 * Represents the results of a plate reading operation.
 * Contains a timestamp and reading values for each well.
 */
public record ReadingResult(
    long timestamp,
    Map<String, Double> wellReadings
) {}
