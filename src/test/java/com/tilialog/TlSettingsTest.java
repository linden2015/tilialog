package com.tilialog;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import java.io.File;

public class TlSettingsTest {

    @Test
    public void get() {
        MatcherAssert.assertThat(
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ).get("main_frame_size_w"),
            Matchers.equalTo("925")
        );
        MatcherAssert.assertThat(
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ).get("main_frame_size_h"),
            Matchers.equalTo("525")
        );
        MatcherAssert.assertThat(
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ).get("stamp_rounds_to_minutes"),
            Matchers.equalTo("5")
        );
    }

    @Test
    public void put() {

        // Put a digit
        TlSettings settings = new TlSettings(
            new FkTextFile("a=1\nb=2\nc=3")
        );
        settings.put("a", "2");
        MatcherAssert.assertThat(settings.get("a"), Matchers.equalTo("2"));

        // Put a letter
        TlSettings settings2 = new TlSettings(
            new FkTextFile("a=1\nb=2\nc=3")
        );
        settings2.put("b", "d");
        MatcherAssert.assertThat(settings2.get("b"), Matchers.equalTo("d"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegal_argument() {
        TlSettings settings = new TlSettings(
            new FkTextFile("a=1\nb=2\nc=3")
        );
        settings.put("a", "1 1");
    }
}
