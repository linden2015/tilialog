import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
public class Report {
    public static String systemEOL = System.getProperty("line.separator");
    private List<EntryRow> logEntries;
    public Report(List<EntryRow> logEntries) {
        this.logEntries = logEntries;
    }
    public String regularAsString() throws IllegalArgumentException, IllegalStateException {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story").append(systemEOL+systemEOL);
        Duration totalTime = Duration.ZERO;
        for (Log log : validatedLogEntries()) {
            sb.append(log.storyCode() + ": ")
                .append(log.duration().getSeconds() / 60 + " min ")
                .append(log.description())
                .append(systemEOL)
            ;
            totalTime = totalTime.plus(log.duration());
        }
        sb.append(systemEOL).append("Total time spent: ")
            .append(String.format(
                "%d hrs %d min",
                totalTime.getSeconds() / 3600,
                (totalTime.getSeconds() % 3600) / 60)
        );
        return sb.toString();
    }
    public String combinedAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story").append(systemEOL+systemEOL);
        Duration totalTime = Duration.ZERO;
        List<Log> logs = validatedLogEntries();
        Set<Integer> appliedLogs = new HashSet<>();
        for (int i = 0; i < logs.size(); i++) {
            if (appliedLogs.contains(i)) {
                continue;
            }
            StringBuilder descriptionI = new StringBuilder()
                .append(systemEOL)
                .append(logs.get(i).description());
            Duration durationI = logs.get(i).duration();
            for (int j = 0; j < logs.size(); j++) {
                if (appliedLogs.contains(j) || (i == j)) {
                    continue;
                }
                if (logs.get(i).storyCode().equals(logs.get(j).storyCode())) {
                    descriptionI.append(systemEOL)
                        .append(logs.get(j).description())
                    ;
                    durationI = durationI.plus(logs.get(j).duration());
                    appliedLogs.add(j);
                }
            }
            sb.append(logs.get(i).storyCode() + ": ")
                .append(durationI.getSeconds() / 60 + " min ")
                .append(descriptionI)
                .append(systemEOL)
            ;
            totalTime = totalTime.plus(durationI);
            appliedLogs.add(i);
        }
        sb.append(systemEOL).append("Total time spent: ")
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
        for (EntryRow entry : logEntries) {
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
    public String toString() {
        StringBuilder sbLog = new StringBuilder("# TiliaLog plain text report #")
            .append(systemEOL);
        sbLog.append("Local datetime: " + LocalDateTime.now().toString())
            .append(systemEOL)
        ;
        for (EntryRow entry : logEntries) {
            if (! entry.isEmpty()) {
                sbLog.append(entry.toString());
            }
        }
        return sbLog.toString();
    }
}
