package io.jlabrobot.platereading;

import java.util.Map;

public record ReadingResult(
    long timestamp,
    Map<String, Double> wellReadings
) {}
