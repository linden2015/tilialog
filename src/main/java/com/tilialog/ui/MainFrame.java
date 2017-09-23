package com.tilialog.ui;

import com.tilialog.AppIsOutdated;
import com.tilialog.Settings;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame {

    private Settings settings;

    private AppIsOutdated appIsOutdated;

    private JFrame frame;

    private LogEntryPanel logEntryPanel;

    private Menu menu;

    public MainFrame(Settings settings, AppIsOutdated appIsOutdated) {
        this.settings = settings;
        this.appIsOutdated = appIsOutdated;
        buildUI();
    }

    private void buildUI() {
        logEntryPanel = new LogEntryPanel(settings);
        menu = new Menu(logEntryPanel, settings, appIsOutdated);

        frame = new JFrame("TiliaLog");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(
            Integer.valueOf(settings.get("main_frame_size_w")),
            Integer.valueOf(settings.get("main_frame_size_h"))
        );
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(menu.menu());
        frame.add(logEntryPanel.panel());
    }

    public void setVisible() {
        frame.setVisible(true);
    }

    public void showUpdatesMessageDialog() {
        menu.showUpdatesMessageDialog();
    }

    public LogEntryPanel logEntryPanel() {
        return logEntryPanel;
    }
}
