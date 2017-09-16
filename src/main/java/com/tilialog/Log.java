package com.tilialog;

import java.time.Duration;
import java.time.LocalTime;

public interface Log extends Comparable<Log> {

    public String storyCode();

    public String startedAt();

    public String endedAt();

    public String description();

    public LocalTime startedTime();

    public LocalTime endedTime();

    public Duration duration();

    public boolean overlaps(Log otherLog);
}
