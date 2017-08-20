package com.tilialog;

public interface LogEntryRow {
    public String story();

    public String startedAt();

    public String endedAt();

    public String description();

    public Boolean isEmpty();

    public String toString();
}
