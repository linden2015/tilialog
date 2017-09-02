package com.tilialog;

import java.io.File;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class TlTextFileTest {

    @Test
    public void readAll() throws IOException {
        MatcherAssert.assertThat(
            new TlTextFile(
                new File("settings.list")
            ).readAll(),
            Matchers.containsString("# Settingsfile")
        );
    }

    @Test
    public void replaceAll() throws IOException {
        File file = new File("settings.list.test");
        try {
            file.createNewFile();
            TextFile textFile = new TlTextFile(file);
            textFile.replaceAll("110011");
            MatcherAssert.assertThat(textFile.readAll(), Matchers.equalTo("110011"));
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
            MatcherAssert.assertThat(textFile.readAll(), Matchers.equalTo("1100112"));
        } finally {
            file.delete();
        }
    }

    @Test(expected = IOException.class)
    public void doesNotExist() throws IOException {
        new TlTextFile(new File("settings.lis")).readAll();
    }
}
