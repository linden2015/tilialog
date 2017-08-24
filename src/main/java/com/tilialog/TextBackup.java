package com.tilialog;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class TextBackup {

    private Object obj;

    public TextBackup(Object obj) {
        this.obj = obj;
    }

    public void write() {
        try {
            File file = new File(
                LocalDate.now() + ".backup.txt"
            );
            if (! file.exists()) {
                file.createNewFile();
            }
            TextFile textFile = new TlTextFile(file);
            textFile.append(obj.toString());
        } catch (IOException e) {
            throw new RuntimeException("Textbackup failed.", e);
        }
    }
}