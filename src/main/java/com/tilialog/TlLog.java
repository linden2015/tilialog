package com.tilialog;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TlLog implements Log {

    private String storyCode;

    private String startedAt;

    private String endedAt;

    private String description;

    public TlLog(
        String storyCode,
        String startedAt,
        String endedAt,
        String description
    ) {
        this.storyCode = storyCode;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.description = description;
    }

    @Override
    public String storyCode() {
        return storyCode;
    }

    @Override
    public Duration duration() {
        return Duration.between(startedTime(), endedTime());
    }

    @Override
    public String startedAt() {
        return startedAt;
    }

    @Override
    public String endedAt() {
        return endedAt;
    }

    @Override
    public LocalTime startedTime() {
        try {
            return LocalTime.parse(startedAt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Text could not be interpreted as datetime: " + startedAt + ".", e
            );
        }
    }

    @Override
    public LocalTime endedTime() {
        try {
            return LocalTime.parse(endedAt);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Text could not be interpreted as datetime: " + endedAt + ".", e
            );
        }
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public int compareTo(Log otherLog) {
        return startedTime().compareTo(
            otherLog.startedTime()
        );
    }

    @Override
    public boolean overlaps(Log otherLog) {
        return startedTime().isBefore(otherLog.endedTime())
            && endedTime().isAfter(otherLog.startedTime())
        ;
    }

    public String toString() {
        return new StringBuilder()
            .append(storyCode())
            .append("    ")
            .append(startedAt)
            .append(" - ")
            .append(endedAt)
            .append("    ")
            .append(description())
            .append("    ")
            .append(duration().getSeconds() / 60 + " min")
            .toString()
        ;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Log)) {
            return false;
        }
        return false;
    }
}
