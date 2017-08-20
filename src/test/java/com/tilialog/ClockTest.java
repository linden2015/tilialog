package com.tilialog;

import com.tilialog.Clock;
import java.time.LocalTime;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClockTest {
    @Test
    public void string() {
        assertEquals("09:00", new Clock(LocalTime.of(9, 0)).asString());
        assertEquals("00:00", new Clock(LocalTime.of(0, 0)).asString());
        assertEquals("12:30", new Clock(LocalTime.of(12, 30)).asString());
    }

    @Test
    public void rounded_string() {
        assertEquals("09:00", new Clock(LocalTime.of(9, 0)).asStringRounded(5));
        assertEquals("09:00", new Clock(LocalTime.of(9, 1)).asStringRounded(5));
        assertEquals("09:00", new Clock(LocalTime.of(9, 2)).asStringRounded(5));
        assertEquals("09:05", new Clock(LocalTime.of(9, 3)).asStringRounded(5));
        assertEquals("09:05", new Clock(LocalTime.of(9, 4)).asStringRounded(5));
        assertEquals("09:05", new Clock(LocalTime.of(9, 5)).asStringRounded(5));
        assertEquals("09:05", new Clock(LocalTime.of(9, 6)).asStringRounded(5));
        assertEquals("09:05", new Clock(LocalTime.of(9, 7)).asStringRounded(5));
        assertEquals("09:10", new Clock(LocalTime.of(9, 8)).asStringRounded(5));
        assertEquals("09:10", new Clock(LocalTime.of(9, 9)).asStringRounded(5));
        assertEquals("09:10", new Clock(LocalTime.of(9, 10)).asStringRounded(5));
        assertEquals("09:10", new Clock(LocalTime.of(9, 11)).asStringRounded(5));
        assertEquals("09:10", new Clock(LocalTime.of(9, 12)).asStringRounded(5));
        assertEquals("09:15", new Clock(LocalTime.of(9, 13)).asStringRounded(5));
        assertEquals("09:15", new Clock(LocalTime.of(9, 14)).asStringRounded(5));
        assertEquals("09:15", new Clock(LocalTime.of(9, 15)).asStringRounded(5));
        assertEquals("09:15", new Clock(LocalTime.of(9, 16)).asStringRounded(5));
        assertEquals("09:15", new Clock(LocalTime.of(9, 17)).asStringRounded(5));
        assertEquals("09:20", new Clock(LocalTime.of(9, 18)).asStringRounded(5));
        assertEquals("09:20", new Clock(LocalTime.of(9, 19)).asStringRounded(5));
        assertEquals("09:20", new Clock(LocalTime.of(9, 20)).asStringRounded(5));
        assertEquals("09:00", new Clock(LocalTime.of(9, 0)).asStringRounded(10));
        assertEquals("09:00", new Clock(LocalTime.of(9, 1)).asStringRounded(10));
        assertEquals("09:00", new Clock(LocalTime.of(9, 2)).asStringRounded(10));
        assertEquals("09:10", new Clock(LocalTime.of(9, 11)).asStringRounded(10));
        assertEquals("09:10", new Clock(LocalTime.of(9, 14)).asStringRounded(10));
        assertEquals("09:20", new Clock(LocalTime.of(9, 15)).asStringRounded(10));
        assertEquals("09:20", new Clock(LocalTime.of(9, 16)).asStringRounded(10));
        assertEquals("09:50", new Clock(LocalTime.of(9, 54)).asStringRounded(10));
        assertEquals("10:00", new Clock(LocalTime.of(9, 55)).asStringRounded(10));
        assertEquals("23:50", new Clock(LocalTime.of(23, 54)).asStringRounded(10));
        assertEquals("00:00", new Clock(LocalTime.of(23, 55)).asStringRounded(10));
        assertEquals("09:00", new Clock(LocalTime.of(9, 1)).asStringRounded(15));
        assertEquals("09:15", new Clock(LocalTime.of(9, 8)).asStringRounded(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegal_argument() {
        new Clock(LocalTime.of(9, 15)).asStringRounded(24);
    }
}
