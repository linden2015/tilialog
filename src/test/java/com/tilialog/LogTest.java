package com.tilialog;

import com.tilialog.Log;
import java.time.Duration;
import java.time.LocalTime;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class LogTest {

    @Test
    public void wellformed() {
        Log log = new Log(
            "AA-1", LocalTime.parse("09:00"), LocalTime.parse("13:00"), "DESCR"
        );
        MatcherAssert.assertThat(log.storyCode(), Matchers.equalTo("AA-1"));
        MatcherAssert.assertThat(log.duration(), Matchers.equalTo(Duration.ofMinutes(240)));
        MatcherAssert.assertThat(log.description(), Matchers.equalTo("DESCR"));
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
        MatcherAssert.assertThat(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1", LocalTime.parse("09:10"), LocalTime.parse("09:30"), ""
                )
            ),
            Matchers.is(true)
        );

        // Other is within
        MatcherAssert.assertThat(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1", LocalTime.parse("09:05"), LocalTime.parse("09:10"), ""
                )
            ),
            Matchers.is(true)
        );

        // Not ended before other starts
        MatcherAssert.assertThat(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1", LocalTime.parse("08:45"), LocalTime.parse("09:05"), ""
                )
            ),
            Matchers.is(true)
        );

        // Other is adjacent after
        MatcherAssert.assertThat(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1", LocalTime.parse("09:15"), LocalTime.parse("09:30"), ""
                )
            ),
            Matchers.is(false)
        );

        // Other is adjacent before
        MatcherAssert.assertThat(
            new Log(
                "AA-1", LocalTime.parse("09:00"), LocalTime.parse("09:15"), ""
            ).overlaps(
                new Log(
                    "AA-1", LocalTime.parse("08:45"), LocalTime.parse("09:00"), ""
                )
            ),
            Matchers.is(false)
        );
    }
}
