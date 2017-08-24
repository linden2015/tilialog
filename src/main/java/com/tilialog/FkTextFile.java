package com.tilialog;

import java.io.IOException;

public class FkTextFile implements TextFile {

    private String text;

    public FkTextFile(String text) {
        this.text = text;
    }

    @Override
    public String readAll() throws IOException {
        return text;
    }

    @Override
    public void replaceAll(String text) throws IOException {
        this.text = text;
    }

    @Override
    public void append(String text) throws IOException {
        this.text += text;
    }
}
