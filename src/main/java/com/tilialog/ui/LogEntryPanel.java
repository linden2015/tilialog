package com.tilialog.ui;

import com.tilialog.LogEntryRow;
import com.tilialog.TextBackup;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LogEntryPanel implements Observer {
    public static String EOL = System.getProperty("line.separator");
    private JPanel totalPanel;
    private JPanel entryPanel;
    private JScrollPane scrollPanel;
    private List<LogEntryRow> logEntryRowPanels = new ArrayList<>();
    private int ENTRY_ROW_PANEL_STARTING_COUNT = 3;
    private Settings settings;

    public LogEntryPanel(Settings settings) {
        this.settings = settings;
        buildUI();
        startBackup();
    }

    private void buildUI() {
        entryPanel = new JPanel();
        scrollPanel = new JScrollPane(
            entryPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        entryPanel.setLayout(new FlowLayout());
        entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        for (int i = 1; i <= ENTRY_ROW_PANEL_STARTING_COUNT; i++) {
            addLogEntryRowPanel();
        }
        totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(new TitlePanel().panel(), BorderLayout.NORTH);
        totalPanel.add(scrollPanel, BorderLayout.CENTER);
    }

    private void addLogEntryRowPanel() {
        LogEntryRowPanel logEntryRowPanel = new LogEntryRowPanel(settings);
        entryPanel.add(logEntryRowPanel.panel());
        logEntryRowPanels.add(logEntryRowPanel);
        logEntryRowPanel.addObserver(this);
        resizeEntryPanel();
    }

    private void startBackup() {
        new Timer(true).scheduleAtFixedRate(
            new TimerTask() {
                @Override
                public void run() {
                    new TextBackup(this).write();
                }
            }, 0, 300_000
        );
    }

    private void resizeEntryPanel() {
        entryPanel.setPreferredSize(new Dimension(
            840, entryPanel.getComponentCount() * 40
        ));
    }

    private void redraw() {
        totalPanel.validate();
        totalPanel.repaint();
    }

    public JPanel panel() {
        return totalPanel;
    }

    public List<LogEntryRow> logEntryRows() {
        return logEntryRowPanels;
    }

    public String toString() {
        StringBuilder sbLog = new StringBuilder();
        sbLog.append("# EntryRows plain text report #")
            .append(EOL);
        sbLog.append("Local datetime: " + LocalDateTime.now().toString())
            .append(EOL);
        for (LogEntryRow entry : logEntryRowPanels) {
            if (! entry.isEmpty()) {
                sbLog.append(entry.toString());
            }
        }
        return sbLog.toString();
    }

    private void addOrRemoveRows() {
        int emptyCount = 0;
        LogEntryRow lastEmptyRow = null;
        for (LogEntryRow row : logEntryRowPanels) {
            if (row.isEmpty()) {
                emptyCount++;
                lastEmptyRow = row;
            }
        }
        if (emptyCount < 3) {
            addLogEntryRowPanel();
            redraw();
        }
        if (emptyCount > 3) {
            ((LogEntryRowPanel) lastEmptyRow).deleteObserver(this);
            logEntryRowPanels.remove(lastEmptyRow);
            entryPanel.remove(((LogEntryRowPanel) lastEmptyRow).panel());
            redraw();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LogEntryRowPanel) {
            addOrRemoveRows();
        }
    }
}