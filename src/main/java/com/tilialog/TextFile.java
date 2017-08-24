package com.tilialog;

import java.io.IOException;

public interface TextFile {

    String readAll() throws IOException;

    void replaceAll(String text) throws IOException;

    void append(String text) throws IOException;
}
