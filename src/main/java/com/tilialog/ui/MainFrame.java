package com.tilialog.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame {
    private Settings settings;
    private JFrame frame;
    private LogEntryPanel logEntryPanel;
    private Menu menu;

    public MainFrame(Settings settings) {
        this.settings = settings;
        buildUI();
    }

    private void buildUI() {
        logEntryPanel = new LogEntryPanel(settings);
        menu = new Menu(logEntryPanel, settings);
        frame = new JFrame("TiliaLog");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(settings.mainFrameSize());
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(menu.menu());
        frame.add(logEntryPanel.panel());
        frame.setVisible(true);
    }

    public LogEntryPanel logEntryPanel() {
        return logEntryPanel;
    }

    /*
    public JFrame frame() {
        return frame;
    }

    public void redraw() {
        frame.validate();
        frame.repaint();
    }
    */
}
