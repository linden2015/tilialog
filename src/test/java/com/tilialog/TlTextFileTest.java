package com.tilialog;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TlTextFileTest {

    @Test
    public void readAll() throws IOException {
        assertEquals(
            "# Settingsfile",
            new TlTextFile(
                new File("settings.list")
            ).readAll().substring(0,14)
        );
    }

    @Test
    public void replaceAll() throws IOException {
        File file = new File("settings.list.test");
        try {
            file.createNewFile();
            TextFile textFile = new TlTextFile(file);
            textFile.replaceAll("110011");
            assertEquals("110011", textFile.readAll());
        } finally {
            file.delete();
        }
    }

    @Test
    public void append() throws IOException {
        File file = new File("settings.list.test");
        try {
            file.createNewFile();
            TextFile textFile = new TlTextFile(file);
            textFile.replaceAll("110011");
            textFile.append("2");
            assertEquals("1100112", textFile.readAll());
        } finally {
            file.delete();
        }
    }

    @Test(expected = IOException.class)
    public void doesNotExist() throws IOException {
        new TlTextFile(new File("settings.lis")).readAll();
    }
}
