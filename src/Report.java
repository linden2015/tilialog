import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
public class Report {
    public static String systemEOL = System.getProperty("line.separator");
    private List<EntryRow> logEntries;
    public Report(List<EntryRow> logEntries) {
        this.logEntries = logEntries;
    }
    public String regularAsString() throws IllegalArgumentException, IllegalStateException {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story");
        sb.append(systemEOL);
        for (EntryRow entry : logEntries) {
            if (entry.story().length() == 0 &&
                entry.startedAt().length() == 0 &&
                entry.endedAt().length() == 0
            ) {
                continue;
            }
            Log log;
            try {
                log = new Log(
                    entry.story(),
                    LocalTime.parse(entry.startedAt()),
                    LocalTime.parse(entry.endedAt())
                );
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    "The entered time could not be understood."
                );
            }
            sb.append(log.storyCode() + ": ");
            sb.append(log.duration().getSeconds() / 60 + " minutes");
            sb.append(systemEOL);
        }
        return sb.toString();
    }
    public String combinedAsString() {
        return "1";
    }
    public String toString() {
        StringBuilder sbLog = new StringBuilder("# TiliaLog plain text report #");
        sbLog.append(systemEOL);
        sbLog.append("Local datetime: " + LocalDateTime.now().toString());
        sbLog.append(systemEOL);
        for (EntryRow entry : logEntries) {
            String entryString = new StringBuilder()
                .append(entry.story())
                .append("\t")
                .append(entry.startedAt())
                .append("\t")
                .append(entry.endedAt())
                .append(systemEOL)
                .toString();
            // Guard for any alphanumeric character in the entry
            if (Pattern.compile("\\w").matcher(entryString).find()) {
                sbLog.append(entryString);
            }
        }
        return sbLog.toString();
    }
}
