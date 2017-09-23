package com.tilialog.ui;

import com.tilialog.AppIsOutdated;
import com.tilialog.AtLeastOneLogs;
import com.tilialog.NonEmptyLogs;
import com.tilialog.NonOverlappingLogs;
import com.tilialog.Report;
import com.tilialog.Settings;
import com.tilialog.ValidatedLogs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Menu {

    private JMenuBar menuBar;

    private LogEntryPanel logEntryPanel;

    private Settings settings;

    private SettingsPanel settingsPanel;

    private AppIsOutdated appIsOutdated;

    public Menu(LogEntryPanel logEntryPanel, Settings settings, AppIsOutdated appIsOutdated) {
        this.logEntryPanel = logEntryPanel;
        this.settings = settings;
        this.appIsOutdated = appIsOutdated;
        buildUI();
    }

    private void buildUI() {
        JMenuItem reportMenuItem = new JMenuItem("Report..");
        reportMenuItem.addActionListener(new ReportMenuItemActionListener());

        JMenuItem clearMenuItem = new JMenuItem("Clear form..");
        clearMenuItem.addActionListener(new ClearMenuItemActionListener());

        JMenuItem settingsMenuItem = new JMenuItem("Settings..");
        settingsMenuItem.addActionListener(new SettingsMenuItemActionListener());

        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.add(reportMenuItem);
        optionsMenu.add(clearMenuItem);
        optionsMenu.add(settingsMenuItem);

        JMenuItem UpdatesItem = new JMenuItem("Check for updates..");
        UpdatesItem.addActionListener(new UpdatesItemActionListener());

        JMenuItem aboutMenuItem = new JMenuItem("About..");
        aboutMenuItem.addActionListener(new AboutMenuItemActionListener());

        JMenu otherMenu = new JMenu("Other");
        otherMenu.add(UpdatesItem);
        otherMenu.add(aboutMenuItem);

        menuBar = new JMenuBar();
        menuBar.add(optionsMenu);
        menuBar.add(otherMenu);
    }

    public JMenuBar menu() {
        return menuBar;
    }

    private class ClearMenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int answer = JOptionPane.showConfirmDialog(
                menuBar,
                "Clear entire form?",
                "Clear",
                JOptionPane.OK_CANCEL_OPTION
            );
            if (answer == JOptionPane.OK_OPTION) {
                logEntryPanel.clearLogEntryRows();
            }
        }
    }

    private class ReportMenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int answer = JOptionPane.showConfirmDialog(
                menuBar,
                "Combine stories?",
                "Report",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            switch (answer) {
                case JOptionPane.YES_OPTION:
                    try {
                        JOptionPane.showMessageDialog(
                            menuBar,
                            new JTextArea(
                                new Report(
                                    new AtLeastOneLogs(
                                        new NonOverlappingLogs(
                                            new ValidatedLogs(
                                                new NonEmptyLogs(
                                                    logEntryPanel.logs()
                                                )
                                            )
                                        )
                                    )
                                ).combinedAsString(),
                                20,
                                50
                            )
                        );
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        JOptionPane.showMessageDialog(menuBar, e.getMessage());
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    try {
                        JOptionPane.showMessageDialog(
                            menuBar,
                            new JTextArea(
                                new Report(
                                    new AtLeastOneLogs(
                                        new NonOverlappingLogs(
                                            new ValidatedLogs(
                                                new NonEmptyLogs(logEntryPanel.logs())
                                            )
                                        )
                                    )
                                ).regularAsString(),
                                20,
                                50
                            )
                        );
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        JOptionPane.showMessageDialog(menuBar, e.getMessage());
                    }
                    break;
            }
        }
    }

    private class SettingsMenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            settingsPanel = new SettingsPanel(settings);
            int answer = JOptionPane.showConfirmDialog(
                menuBar,
                settingsPanel.panel(),
                "Settings",
                JOptionPane.OK_CANCEL_OPTION
            );
            switch (answer) {
                case JOptionPane.YES_OPTION:
                    settings.put(
                        "stamp_rounds_to_minutes",
                        settingsPanel.roundStampTo()
                    );
                    break;
            }
        }
    }

    private class AboutMenuItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(
                menuBar,
                new AboutPanel().panel(),
                "About",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private class UpdatesItemActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            showUpdatesMessageDialog();
        }
    }

    public void showUpdatesMessageDialog() {
        JOptionPane.showMessageDialog(
            menuBar,
            new UpdatesPanel(appIsOutdated).panel(),
            "Updates",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}