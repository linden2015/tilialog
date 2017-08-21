package com.tilialog;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable {
    private long serialVersionUID = 1L;

    private File settingsFile;

    private int roundStampTo = 5;

    private Dimension mainFrameSize = new Dimension(925, 525);

    public Settings(File settingsFile) {
        this.settingsFile = settingsFile;
        if (settingsFile.exists()) {
            loadFromStorage();
        }
    }

    public int roundStampTo() {
        return roundStampTo;
    }

    public void setRoundStampTo(int roundStampTo) {
        this.roundStampTo = roundStampTo;
        saveToStorage();
    }

    public Dimension mainFrameSize() {
        return mainFrameSize;
    }

    private void loadFromStorage() {
        try {
            ObjectInputStream stream = new ObjectInputStream(
                new FileInputStream(settingsFile)
            );
            Settings settingsFromStorage = (Settings) stream.readObject();
            roundStampTo = settingsFromStorage.roundStampTo;
            mainFrameSize = settingsFromStorage.mainFrameSize;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(
                "Unrecoverable error while loading settings from storage.", e
            );
        }
    }

    public void saveToStorage() {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(settingsFile)
            );
            stream.writeObject(this);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(
                "Unrecoverable error while saving settings to storage.", e
            );
        }
    }
}