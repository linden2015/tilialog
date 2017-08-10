import org.junit.Test;
import java.time.Duration;
import java.time.LocalTime;
import static org.junit.Assert.assertEquals;
public class LogTest {
    @Test
    public void wellformed() {
        Log log = new Log(
            "AA-1", LocalTime.parse("09:00"), LocalTime.parse("13:00")
        );
        assertEquals("AA-1", log.storyCode());
        assertEquals(Duration.ofMinutes(240), log.duration());
    }
    @Test(expected = IllegalStateException.class)
    public void missing_storycode() {
        new Log(
            "", LocalTime.parse("09:00"), LocalTime.parse("13:00")
        ).storyCode();
    }
}
