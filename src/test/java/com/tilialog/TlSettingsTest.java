package com.tilialog;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

public class TlSettingsTest {

    @Test
    public void get() {
        assertEquals(
            "925",
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ).get("main_frame_size_w")
        );
        assertEquals(
            "525",
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ).get("main_frame_size_h")
        );
        assertEquals(
            "5",
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ).get("stamp_rounds_to_minutes")
        );
    }

    @Test
    public void put() {
        TlSettings settings = new TlSettings(
            new FkTextFile("a=1\nb=2\nc=3")
        );
        settings.put("a", "2");
        assertEquals("2", settings.get("a"));

        TlSettings settings3 = new TlSettings(
            new FkTextFile("a=1\nb=2\nc=3")
        );
        settings3.put("b", "d");
        assertEquals("d", settings3.get("b"));
    }
}
