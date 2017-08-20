package com.tilialog;

import com.tilialog.Log;
import java.time.Duration;
import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogTest {
    @Test
    public void wellformed() {
        Log log = new Log(
            "AA-1", LocalTime.parse("09:00"), LocalTime.parse("13:00"), "DESCR"
        );
        assertEquals("AA-1", log.storyCode());
        assertEquals(Duration.ofMinutes(240), log.duration());
        assertEquals("DESCR", log.description());
    }

    @Test(expected = IllegalStateException.class)
    public void missing_storycode() {
        new Log(
            "", LocalTime.parse("09:00"), LocalTime.parse("13:00"), ""
        ).storyCode();
    }

    @Test(expected = IllegalStateException.class)
    public void negative_duration() {
        new Log(
            "AA-1", LocalTime.parse("13:00"), LocalTime.parse("09:00"), ""
        ).duration();
    }

    @Test
    public void overlaps() {
        // Starts before other ends
        assertTrue(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1",
                    LocalTime.parse("09:10"),
                    LocalTime.parse("09:30"),
                    ""
                )
            )
        );
        // Within
        assertTrue(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1",
                    LocalTime.parse("09:05"),
                    LocalTime.parse("09:10"),
                    ""
                )
            )
        );
        // Not ended before other starts
        assertTrue(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1",
                    LocalTime.parse("08:45"),
                    LocalTime.parse("09:05"),
                    ""
                )
            )
        );
        // Adjacent 1
        assertFalse(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1",
                    LocalTime.parse("09:15"),
                    LocalTime.parse("09:30"),
                    ""
                )
            )
        );
        // Adjacent 2
        assertFalse(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1",
                    LocalTime.parse("08:45"),
                    LocalTime.parse("09:00"),
                    ""
                )
            )
        );
    }
}
