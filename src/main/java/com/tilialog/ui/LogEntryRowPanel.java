package com.tilialog.ui;

import com.tilialog.Clock;
import com.tilialog.LogEntryRow;
import com.tilialog.Settings;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalTime;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogEntryRowPanel extends Observable implements LogEntryRow {
    public static String EOL = System.getProperty("line.separator");
    private JPanel panel = new JPanel();
    private JTextField storyField = new JTextField();
    private JTextField startedAtField = new JTextField();
    private JTextField endedAtField = new JTextField();
    private JTextField descriptionField = new JTextField();
    private JButton stampTime = new JButton("Stamp");
    public int PANEL_HEIGHT = 30;
    private Settings settings;

    public LogEntryRowPanel(Settings settings) {
        this.settings = settings;
        buildUI();
    }

    private void buildUI() {
        panel.setPreferredSize(new Dimension(800, PANEL_HEIGHT));
        panel.setLayout(new FlowLayout());
        storyField.setPreferredSize(new Dimension(100, 25));
        TextFieldKeyListener textFieldKeyListener = new TextFieldKeyListener();
        storyField.addKeyListener(textFieldKeyListener);
        panel.add(storyField);
        startedAtField.setPreferredSize(new Dimension(100, 25));
        startedAtField.addKeyListener(textFieldKeyListener);
        panel.add(startedAtField);
        endedAtField.setPreferredSize(new Dimension(100, 25));
        endedAtField.addKeyListener(textFieldKeyListener);
        panel.add(endedAtField);
        descriptionField.setPreferredSize(new Dimension(300, 25));
        descriptionField.addKeyListener(textFieldKeyListener);
        panel.add(descriptionField);
        stampTime.setToolTipText(
            "Enters the current time to the next available field"
        );
        stampTime.addActionListener(new StampTimeActionListener());
        panel.add(stampTime);
    }

    private class TextFieldKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            setChanged();
            notifyObservers();
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class StampTimeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String now;
            try {
                now = new Clock(
                    LocalTime.now()
                ).asStringRounded(
                    Integer.valueOf(
                        settings.get("stamp_rounds_to_minutes")
                    )
                );
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(panel, e.getMessage());
                return;
            }
            // Determine which field to stamp or notify failure
            if (startedAt().length() == 0) {
                startedAtField.setText(now);
            } else if (endedAt().length() == 0) {
                endedAtField.setText(now);
            } else {
                JOptionPane.showMessageDialog(panel,
                    "No available field to enter current time to."
                );
            }
            setChanged();
            notifyObservers();
        }
    }

    public JPanel panel() {
        return panel;
    }

    @Override
    public String story() {
        return storyField.getText();
    }

    @Override
    public String startedAt() {
        return startedAtField.getText();
    }

    @Override
    public String endedAt() {
        return endedAtField.getText();
    }

    @Override
    public String description() {
        return descriptionField.getText();
    }

    @Override
    public Boolean isEmpty() {
        return story().length() == 0 &&
            startedAt().length() == 0 &&
            endedAt().length() == 0;
    }

    public String toString() {
        return new StringBuilder()
            .append(story())
            .append("\t")
            .append(startedAt())
            .append("\t")
            .append(endedAt())
            .append("\t")
            .append(description())
            .append(EOL)
            .toString();
    }
}
