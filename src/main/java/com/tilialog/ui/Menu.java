package com.tilialog.ui;

import com.tilialog.Report;
import java.awt.Dimension;
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

    public Menu(LogEntryPanel logEntryPanel, Settings settings) {
        this.logEntryPanel = logEntryPanel;
        this.settings = settings;
        buildUI();
    }

    private void buildUI() {
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem titleMenuItem = new JMenuItem("Report");
        titleMenuItem.addActionListener(new titleMenuItemActionListener());
        optionsMenu.add(titleMenuItem);
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        settingsMenuItem.addActionListener(new settingsMenuItemActionListener());
        optionsMenu.add(settingsMenuItem);
        menuBar = new JMenuBar();
        menuBar.add(optionsMenu);
        JMenu otherMenu = new JMenu("Other");
        otherMenu.add(new JMenuItem("About"));
        menuBar.add(otherMenu);
    }

    public JMenuBar menu() {
        return menuBar;
    }

    private class titleMenuItemActionListener implements ActionListener {
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
                                    logEntryPanel.logEntryRows()
                                ).combinedAsString(),
                                20,
                                50
                            )
                        );
                    } catch (
                        IllegalArgumentException | IllegalStateException e
                    ) {
                        JOptionPane.showMessageDialog(menuBar, e.getMessage());
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    try {
                        JOptionPane.showMessageDialog(
                            menuBar,
                            new JTextArea(
                                new Report(
                                    logEntryPanel.logEntryRows()
                                ).regularAsString(),
                                20,
                                50
                            )
                        );
                    } catch (
                        IllegalArgumentException | IllegalStateException e
                    ) {
                        JOptionPane.showMessageDialog(menuBar, e.getMessage());
                    }
                    break;
            }
        }
    }
    private class settingsMenuItemActionListener implements ActionListener {
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
                    settings.setRoundStampTo(settingsPanel.roundStampTo());
                    break;
            }
        }
    }
}
