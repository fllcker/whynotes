package ru.fllcker.whynotes.utils;

import java.time.Instant;

public class TimeUtils {
    public static Long getNextTime(Long startTime, Long intervalTime) {
        Instant nowUnix = Instant.ofEpochSecond(startTime);
        Instant result = nowUnix.plusSeconds(intervalTime);
        return result.getEpochSecond();
    }

    public static Long nowLong() {
        return Instant.now().getEpochSecond();
    }
}
