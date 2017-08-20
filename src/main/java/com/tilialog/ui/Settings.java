package com.tilialog.ui;

import java.awt.Dimension;

public class Settings {
    private int roundStampTo = 5;

    public int roundStampTo() {
        return roundStampTo;
    }

    public void setRoundStampTo(int roundStampTo) {
        this.roundStampTo = roundStampTo;
    }

    public Dimension mainFrameSize() {
        return new Dimension(925, 525);
    }
}
