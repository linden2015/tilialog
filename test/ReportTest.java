import org.junit.Test;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
public class ReportTest {
    @Test
    public void basicToString() {
        List<EntryRow> entries = new ArrayList<>();
        EntryRow entry = new EntryRow() {
            @Override
            public String story() {
                return "STORY-01";
            }
            @Override
            public String startedAt() {
                return "09:30";
            }
            @Override
            public String endedAt() {
                return "09:45";
            }
        };
        entries.add(entry);
        String stringReport = new Report(entries).toString();
        assertEquals(true, stringReport.contains("TiliaLog"));
        assertEquals(true, stringReport.contains("STORY-01"));
        assertEquals(true, stringReport.contains("Local datetime"));
        assertEquals(true, stringReport.contains("STORY-01\t09:30\t09:45"));
        System.out.println(stringReport);
    }
}
