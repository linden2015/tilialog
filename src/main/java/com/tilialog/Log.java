package com.tilialog;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Log implements Comparable<Log> {
    private String description;
    private String storyCode;
    private LocalTime startTime;
    private LocalTime endTime;

    public Log(
        String storyCode,
        LocalTime startTime,
        LocalTime endTime,
        String description
    ) {
        this.storyCode = storyCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public String storyCode() {
        if (storyCode.length() == 0) {
            throw new IllegalStateException("Description is empty.");
        }
        return storyCode;
    }

    public Duration duration() {
        Duration duration = Duration.between(startTime, endTime);
        if (duration.isNegative()) {
            throw new IllegalStateException("Duration is negative.");
        }
        return duration;
    }

    public LocalTime startTime() {
        return startTime;
    }

    public LocalTime endTime() {
        return endTime;
    }

    public String description() {
        return description;
    }

    @Override
    public int compareTo(Log otherLog) {
        return startTime.compareTo(otherLog.startTime());
    }

    public boolean overlaps(Log otherLog) {
        return startTime.isBefore(otherLog.endTime()) &&
            endTime.isAfter(otherLog.startTime());
    }

    public String toString() {
        return new StringBuilder()
            .append(storyCode())
            .append(". ")
            .append(startTime.format(DateTimeFormatter.ofPattern("HH:mm")))
            .append(" - ")
            .append(endTime.format(DateTimeFormatter.ofPattern("HH:mm")))
            .append(". ")
            .append(description())
            .append(". ")
            .append(duration().getSeconds() / 60 + " min")
            .append(".")
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Log)) {
            return false;
        }
        return false;
    }
}
