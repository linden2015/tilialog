package com.tilialog;

import java.time.Duration;
import java.time.LocalTime;

public class ValidatedLog implements Log {

    private Log log;

    public ValidatedLog(Log log) {
        this.log = log;
    }

    @Override
    public String storyCode() {
        if (log.storyCode().isEmpty()) {
            throw new IllegalStateException("Storycode is empty");
        }
        return log.storyCode();
    }

    @Override
    public String startedAt() {
        if (log.startedAt().isEmpty()) {
            throw new IllegalStateException("Started at is empty");
        }
        return log.startedAt();
    }

    @Override
    public String endedAt() {
        if (log.endedAt().isEmpty()) {
            throw new IllegalStateException("Ended at is empty");
        }
        return log.endedAt();
    }

    @Override
    public String description() {
        return log.description();
    }

    @Override
    public LocalTime startedTime() {
        startedAt();
        endedAt();
        try {
            log.startedTime();
            log.endedTime();
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
        }
        if (log.duration().isNegative()) {
            throw new IllegalStateException("Log starttime is after endtime.");
        }
        return log.startedTime();
    }

    @Override
    public LocalTime endedTime() {
        endedAt();
        startedAt();
        try {
            log.endedTime();
            log.startedTime();
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
        }
        if (log.duration().isNegative()) {
            throw new IllegalStateException("Log endtime is before starttime.");
        }
        return log.endedTime();
    }

    @Override
    public Duration duration() {
        startedTime();
        endedTime();
        return log.duration();
    }

    @Override
    public boolean overlaps(Log otherLog) {
        startedTime();
        endedTime();
        return log.overlaps(otherLog);
    }

    @Override
    public int compareTo(Log otherLog) {
        startedTime();
        return log.compareTo(otherLog);
    }

    @Override
    public String toString() {
        storyCode();
        startedTime();
        endedTime();
        description();
        return log.toString();
    }
}
