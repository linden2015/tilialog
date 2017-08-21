package com.tilialog.ui;

import com.tilialog.Settings;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SettingsPanel {
    private JPanel panel;
    private Settings settings;
    private JSpinner numberSpinner;

    public SettingsPanel(Settings settings) {
        this.settings = settings;
        buildUI();
    }

    private void buildUI() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        panel.add(new JLabel("Round stamps to: "));
        numberSpinner = new JSpinner(
            new SpinnerNumberModel(
                settings.roundStampTo(), 1, 30, 1
            )
        );
        panel.add(numberSpinner);
    }

    public int roundStampTo() {
        return (int) numberSpinner.getModel().getValue();
    }

    public JPanel panel() {
        return panel;
    }
}
