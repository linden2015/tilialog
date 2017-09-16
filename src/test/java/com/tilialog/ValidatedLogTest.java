package com.tilialog;

import com.tilialog.Log;
import java.time.Duration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ValidatedLogTest {

    @Test
    public void wellformed() {
        Log log = new ValidatedLog(
            new TlLog("AA-1", "09:00", "13:00", "DESCR")
        );
        MatcherAssert.assertThat(log.storyCode(), Matchers.equalTo("AA-1"));
        MatcherAssert.assertThat(log.duration(), Matchers.equalTo(Duration.ofMinutes(240)));
        MatcherAssert.assertThat(log.description(), Matchers.equalTo("DESCR"));
    }

    @Test(expected = IllegalStateException.class)
    public void missing_storycode() {
        new ValidatedLog(
            new TlLog("", "09:00", "13:00", "")
        ).storyCode();
    }

    @Test(expected = IllegalStateException.class)
    public void negative_duration() {
        new ValidatedLog(
            new TlLog("AA-1", "13:00", "09:00", "")
        ).duration();
    }

    @Test
    public void overlaps() {

        // Starts before other ends
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).overlaps(
                new ValidatedLog(
                    new TlLog("AA-1", "09:10", "09:30", "")
                )
            ),
            Matchers.is(true)
        );

        // Other is within
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).overlaps(
                new ValidatedLog(
                    new TlLog("AA-1", "09:05", "09:10", "")
                )
            ),
            Matchers.is(true)
        );

        // Not ended before other starts
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).overlaps(
                new ValidatedLog(
                    new TlLog("AA-1", "08:45", "09:05", "")
                )
            ),
            Matchers.is(true)
        );

        // Other is adjacent after
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).overlaps(
                new ValidatedLog(
                    new TlLog("AA-1", "09:15", "09:30", "")
                )
            ),
            Matchers.is(false)
        );

        // Other is adjacent before
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).overlaps(
                new ValidatedLog(
                    new TlLog("AA-1", "08:45", "09:00", "")
                )
            ),
            Matchers.is(false)
        );
    }

    @Test
    public void compareTest() {

        // Starts before other starts
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).compareTo(
                new ValidatedLog(
                    new TlLog("AA-1", "09:15", "09:30", "")
                )
            ),
            Matchers.is(-1)
        );

        // Starts at the same time as other starts
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "09:00", "09:15", "")
            ).compareTo(
                new ValidatedLog(
                    new TlLog("AA-1", "09:00", "09:30", "")
                )
            ),
            Matchers.is(0)
        );

        // Starts after other starts
        MatcherAssert.assertThat(
            new ValidatedLog(
                new TlLog("AA-1", "10:00", "10:15", "")
            ).compareTo(
                new ValidatedLog(
                    new TlLog("AA-1", "09:15", "09:30", "")
                )
            ),
            Matchers.is(1)
        );
    }
}
