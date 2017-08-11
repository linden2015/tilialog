import org.junit.Test;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
public class ReportTest {
    @Test
    public void basicToString() {
        List<JTextField> entry = Arrays.<JTextField>asList(
            new JTextField("1"), new JTextField("2"), new JTextField("3")
        );
        List<List<JTextField>> entries = new ArrayList<>();
        entries.add(entry);
        String stringReport = new Report(entries).toString();
        assertEquals(true, stringReport.contains("TiliaLog"));
        assertEquals(true, stringReport.contains("Local datetime"));
        assertEquals(true, stringReport.contains("1\t2\t3"));
        System.out.println(stringReport);
    }
}
