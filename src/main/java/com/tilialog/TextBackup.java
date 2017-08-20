package com.tilialog;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class TextBackup {
    private Object obj;

    public TextBackup(Object obj) {
        this.obj = obj;
    }

    public void write() {
        try {
            FileWriter fw = new FileWriter(
                LocalDate.now().toString() + ".backup.txt", true
            );
            fw.append(obj.toString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Textbackup failed.", e);
        }
    }
}