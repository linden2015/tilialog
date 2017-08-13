import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
                .append(log.duration().getSeconds() / 60 + " min. ")
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
    public String combinedAsString() {
        return "1";
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
