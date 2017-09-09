package com.tilialog.ui;

import com.tilialog.Clock;
import com.tilialog.LogEntryRow;
import com.tilialog.Settings;
import com.tilialog.TlLogEntryRow;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.time.LocalTime;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogEntryRowPanel extends Observable {
    public static String EOL = System.getProperty("line.separator");
    private JPanel panel = new JPanel();
    private JTextField storyField;
    private JTextField startedAtField;
    private JTextField endedAtField;
    private JTextField descriptionField;
    private JButton stampTime = new JButton("Stamp");
    public int PANEL_HEIGHT = 30;
    private Settings settings;
    private LogEntryRow row;

    public LogEntryRowPanel(Settings settings, LogEntryRow row) {
        this.settings = settings;
        this.row = row;
        buildUI();
    }

    private void buildUI() {
        panel.setPreferredSize(new Dimension(800, PANEL_HEIGHT));
        panel.setLayout(new FlowLayout());
        storyField = new JTextField(row.story());
        storyField.setPreferredSize(new Dimension(100, 25));
        TextFieldKeyListener textFieldKeyListener = new TextFieldKeyListener();
        storyField.addKeyListener(textFieldKeyListener);
        panel.add(storyField);
        startedAtField = new JTextField(row.startedAt());
        startedAtField.setPreferredSize(new Dimension(100, 25));
        startedAtField.addKeyListener(textFieldKeyListener);
        panel.add(startedAtField);
        endedAtField = new JTextField(row.endedAt());
        endedAtField.setPreferredSize(new Dimension(100, 25));
        endedAtField.addKeyListener(textFieldKeyListener);
        panel.add(endedAtField);
        descriptionField = new JTextField(row.description());
        descriptionField.setPreferredSize(new Dimension(300, 25));
        descriptionField.addKeyListener(textFieldKeyListener);
        panel.add(descriptionField);
        stampTime.setToolTipText("Enters the current time to the next available field");
        stampTime.addActionListener(new StampTimeActionListener());
        panel.add(stampTime);
    }

    private class TextFieldKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            setChanged();
            notifyObservers();
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
            if (startedAtField.getText().length() == 0) {
                startedAtField.setText(now);
            } else if (endedAtField.getText().length() == 0) {
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

    public void clearAll() {
        storyField.setText("");
        startedAtField.setText("");
        endedAtField.setText("");
        descriptionField.setText("");
    }

    public LogEntryRow logEntryRow() {
        return new TlLogEntryRow(
            storyField.getText(),
            startedAtField.getText(),
            endedAtField.getText(),
            descriptionField.getText()
        );
    }
}
