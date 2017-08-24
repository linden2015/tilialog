package com.tilialog;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Report {
    public static String EOL = System.getProperty("line.separator");
    private List<LogEntryRow> logEntryRows;

    public Report(List<LogEntryRow> logEntryRows) {
        this.logEntryRows = logEntryRows;
    }

    public String regularAsString() throws IllegalArgumentException, IllegalStateException {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story").append(EOL + EOL);
        Duration totalTime = Duration.ZERO;
        for (Log log : validatedLogEntries()) {
            sb.append(log.toString())
                .append(EOL);
            totalTime = totalTime.plus(log.duration());
        }
        sb.append(EOL).append("Total time spent: ")
            .append(String.format(
                "%d hrs %d min",
                totalTime.getSeconds() / 3600,
                (totalTime.getSeconds() % 3600) / 60)
            );
        return sb.toString();
    }

    public String combinedAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story").append(EOL + EOL);
        Duration totalTime = Duration.ZERO;
        List<Log> logs = validatedLogEntries();
        Set<Integer> appliedLogs = new HashSet<>();
        for (int i = 0; i < logs.size(); i++) {
            if (appliedLogs.contains(i)) {
                continue;
            }
            StringBuilder descriptionI = new StringBuilder()
                .append(EOL)
                .append("\t")
                .append(logs.get(i).toString());
            Duration durationI = logs.get(i).duration();
            for (int j = 0; j < logs.size(); j++) {
                if (appliedLogs.contains(j) || (i == j)) {
                    continue;
                }
                if (logs.get(i).storyCode().equals(logs.get(j).storyCode())) {
                    descriptionI
                        .append(EOL)
                        .append("\t")
                        .append(logs.get(j).toString());
                    durationI = durationI.plus(logs.get(j).duration());
                    appliedLogs.add(j);
                }
            }
            sb.append(logs.get(i).storyCode() + ": ")
                .append(durationI.getSeconds() / 60 + " min")
                .append(descriptionI)
                .append(EOL);
            totalTime = totalTime.plus(durationI);
            appliedLogs.add(i);
        }
        sb.append(EOL).append("Total time spent: ")
            .append(String.format(
                "%d hrs %d min",
                totalTime.getSeconds() / 3600,
                (totalTime.getSeconds() % 3600) / 60)
            );
        return sb.toString();
    }

    private List<Log> validatedLogEntries() {
        // Parse log entries into a list of logs
        ArrayList<Log> logs = new ArrayList<>();
        for (LogEntryRow entry : logEntryRows) {
            // Skip empty entries
            if (entry.isEmpty()) {
                continue;
            }
            // Parse time elements
            LocalTime parsedStartedAt;
            LocalTime parsedEndedAt;
            try {
                parsedStartedAt = LocalTime.parse(entry.startedAt());
                parsedEndedAt = LocalTime.parse(entry.endedAt());
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    "The text could not be understood as a time: "
                        + e.getParsedString()
                );
            }
            // Add new log to list
            logs.add(new Log(
                entry.story(), parsedStartedAt, parsedEndedAt, entry.description()
            ));
        }
        // Check for at least one log
        if (logs.size() == 0) {
            throw new IllegalStateException("There are no logs.");
        }
        // Check for overlap between logs
        logs.sort(null);
        Iterator<Log> logsIterator = logs.iterator();
        Log currentLog = logsIterator.next();
        while (logsIterator.hasNext()) {
            Log nextLog = logsIterator.next();
            if (currentLog.overlaps(nextLog)) {
                throw new IllegalStateException(
                    "Two logs are overlapping: "
                        + currentLog.toString() + " and " + nextLog.toString()
                );
            }
            currentLog = nextLog;
        }
        return logs;
    }
}
