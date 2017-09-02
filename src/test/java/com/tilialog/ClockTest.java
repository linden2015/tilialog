package com.tilialog;

import com.tilialog.Clock;
import java.time.LocalTime;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class ClockTest {

    @Test
    public void string() {
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 0)).asString(), Matchers.equalTo("09:00")
        );

        MatcherAssert.assertThat(
            new Clock(LocalTime.of(0, 0)).asString(), Matchers.equalTo("00:00")
        );

        MatcherAssert.assertThat(
            new Clock(LocalTime.of(12, 30)).asString(), Matchers.equalTo("12:30")
        );
    }

    @Test
    public void rounded_string() {
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 0)).asStringRounded(5), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 1)).asStringRounded(5), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 2)).asStringRounded(5), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 3)).asStringRounded(5), Matchers.equalTo("09:05")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 4)).asStringRounded(5), Matchers.equalTo("09:05")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 5)).asStringRounded(5), Matchers.equalTo("09:05")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 6)).asStringRounded(5), Matchers.equalTo("09:05")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 7)).asStringRounded(5), Matchers.equalTo("09:05")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 8)).asStringRounded(5), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 9)).asStringRounded(5), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 10)).asStringRounded(5), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 11)).asStringRounded(5), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 12)).asStringRounded(5), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 13)).asStringRounded(5), Matchers.equalTo("09:15")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 14)).asStringRounded(5), Matchers.equalTo("09:15")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 15)).asStringRounded(5), Matchers.equalTo("09:15")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 16)).asStringRounded(5), Matchers.equalTo("09:15")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 17)).asStringRounded(5), Matchers.equalTo("09:15")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 18)).asStringRounded(5), Matchers.equalTo("09:20")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 19)).asStringRounded(5), Matchers.equalTo("09:20")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 20)).asStringRounded(5), Matchers.equalTo("09:20")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 0)).asStringRounded(10), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 1)).asStringRounded(10), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 2)).asStringRounded(10), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 11)).asStringRounded(10), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 14)).asStringRounded(10), Matchers.equalTo("09:10")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 15)).asStringRounded(10), Matchers.equalTo("09:20")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 16)).asStringRounded(10), Matchers.equalTo("09:20")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 54)).asStringRounded(10), Matchers.equalTo("09:50")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 55)).asStringRounded(10), Matchers.equalTo("10:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(23, 54)).asStringRounded(10), Matchers.equalTo("23:50")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(23, 55)).asStringRounded(10), Matchers.equalTo("00:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 1)).asStringRounded(15), Matchers.equalTo("09:00")
        );
        MatcherAssert.assertThat(
            new Clock(LocalTime.of(9, 8)).asStringRounded(15), Matchers.equalTo("09:15")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegal_argument() {
        new Clock(LocalTime.of(9, 15)).asStringRounded(24);
    }
}
