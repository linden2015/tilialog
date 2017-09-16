package com.tilialog;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Report {

    public static String EOL = System.getProperty("line.separator");

    private List<Log> logs;

    public Report(Logs logs) {
        this.logs = logs.get();
    }

    public String regularAsString() throws IllegalArgumentException, IllegalStateException {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story").append(EOL + EOL);
        Duration totalTime = Duration.ZERO;
        for (Log log : logs) {
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
        Set<Integer> appliedLogs = new HashSet<>();
        for (int i = 0; i < logs.size(); i++) {
            if (appliedLogs.contains(i)) {
                continue;
            }
            StringBuilder descriptionI = new StringBuilder()
                .append(EOL)
                .append("    ")
                .append(logs.get(i).toString());
            Duration durationI = logs.get(i).duration();
            for (int j = 0; j < logs.size(); j++) {
                if (appliedLogs.contains(j) || (i == j)) {
                    continue;
                }
                if (logs.get(i).storyCode().equals(logs.get(j).storyCode())) {
                    descriptionI
                        .append(EOL)
                        .append("    ")
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
}
