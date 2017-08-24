package com.tilialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TlTextFile implements TextFile {

    private File file;

    public TlTextFile(File file) {
        this.file = file;
    }

    @Override
    public String readAll() throws IOException {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(file));
        ) {
            StringBuilder fileAsString = new StringBuilder();
            int nextInt = reader.read();
            while (nextInt != - 1) {
                fileAsString.append((char) nextInt);
                nextInt = reader.read();
            }
            return fileAsString.toString();
        }
    }

    @Override
    public void replaceAll(String text) throws IOException {
        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        ) {
            writer.write(text);
        }
    }

    @Override
    public void append(String text) throws IOException {
        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        ) {
            writer.write(text);
        }
    }

    @Override
    public String toString() {
        return file.getPath();
    }
}
