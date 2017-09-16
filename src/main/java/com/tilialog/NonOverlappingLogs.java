package com.tilialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NonOverlappingLogs implements Logs {

    private Logs logs;

    public NonOverlappingLogs(Logs logs) {
        this.logs = logs;
    }

    @Override
    public List<Log> get() {
        ArrayList<Log> logsCopy = new ArrayList<>(logs.get());
        logsCopy.sort(null);
        Iterator<Log> logsIterator = logsCopy.iterator();
        Log currentLog = logsIterator.next();
        while (logsIterator.hasNext()) {
            Log nextLog = logsIterator.next();
            if (currentLog.overlaps(nextLog)) {
                throw new IllegalStateException(
                    new StringBuilder("Logs are overlapping: ")
                        .append("\n")
                        .append(currentLog.storyCode()).append("  ")
                        .append(currentLog.startedAt()).append("  ")
                        .append(currentLog.endedAt())
                        .append("\n")
                        .append(nextLog.storyCode()).append("  ")
                        .append(nextLog.startedAt()).append("  ")
                        .append(nextLog.endedAt())
                        .toString()
                );
            }
            currentLog = nextLog;
        }
        return logs.get();
    }
}
