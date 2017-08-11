import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
public class Report {
    public static String systemEOL = System.getProperty("line.separator");
    private List<List<JTextField>> logEntries;
    public Report(List<List<JTextField>> logEntries) {
        this.logEntries = logEntries;
    }
    public String regularAsString() throws IllegalArgumentException, IllegalStateException {
        StringBuilder sb = new StringBuilder();
        sb.append("Time spent per story");
        sb.append(systemEOL);
        for(List<JTextField> entry : logEntries) {
            String storyString = entry.get(0).getText();
            String startedAtString = entry.get(1).getText();
            String endedAtString = entry.get(2).getText();
            if (
                storyString.length() == 0 &&
                startedAtString.length() == 0 &&
                endedAtString.length() == 0
            ) {
                continue;
            }
            Log log;
            try {
                log = new Log(
                    entry.get(0).getText(),
                    LocalTime.parse(entry.get(1).getText()),
                    LocalTime.parse(entry.get(2).getText())
                );
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                    "The entered time could not be understood."
                );
            }
            sb.append(log.storyCode() + ": ");
            sb.append(log.duration().getSeconds()/60 + " minutes");
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
        for (List<JTextField> logEntry : logEntries) {
            StringBuilder sbEntry = new StringBuilder();
            for (JTextField textField: logEntry) {
                sbEntry.append(textField.getText());
                sbEntry.append("\t");
            }
            sbEntry.append(systemEOL);
            sbEntry.toString();
            if (Pattern.compile("\\w").matcher(sbEntry).find()) {
                sbLog.append(sbEntry);
            }
        }
        return sbLog.toString();
    }
}
