package com.tilialog.ui;

import com.tilialog.DefaultLogs;
import com.tilialog.Log;
import com.tilialog.LogEntryRow;
import com.tilialog.Logs;
import com.tilialog.PersistedLogs;
import com.tilialog.Settings;
import com.tilialog.TlLogEntryRow;
import com.tilialog.TlTextFile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LogEntryPanel implements Observer {

    public static String EOL = System.getProperty("line.separator");

    private JPanel totalPanel;

    private JPanel entryPanel = null;

    private JScrollPane scrollPanel;

    private List<LogEntryRowPanel> logEntryRowPanels = new ArrayList<>();

    private Settings settings;

    private PersistedLogs persistedLogs;

    public LogEntryPanel(Settings settings) {
        this.settings = settings;
        persistedLogs = new PersistedLogs(
            new TlTextFile(
                new File("log-entry-rows.list")
            ),
            new String(new byte[]{0x1E})
        );
        buildUI();
    }

    private void buildUI() {
        entryPanel = new JPanel();
        entryPanel.setSize(new Dimension(840, 600));
        entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel fixedSizePanel = new JPanel();
        fixedSizePanel.add(entryPanel);
        for (LogEntryRow row : persistedLogs.load()) {
            addLogEntryRowPanel(row);
        }
        scrollPanel = new JScrollPane(
            fixedSizePanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(new TitlePanel().panel(), BorderLayout.NORTH);
        totalPanel.add(scrollPanel, BorderLayout.CENTER);
        determineAndApplyEmptyRows();
    }

    private void addLogEntryRowPanel(LogEntryRow row) {
        LogEntryRowPanel logEntryRowPanel = new LogEntryRowPanel(settings, row);
        entryPanel.add(logEntryRowPanel.panel());
        logEntryRowPanels.add(logEntryRowPanel);
        logEntryRowPanel.addObserver(this);
        resizeEntryPanel();
    }

    private void resizeEntryPanel() {
        entryPanel.setPreferredSize(
            new Dimension(
                840,
                entryPanel.getComponentCount() * 40
            )
        );
    }

    private void redraw() {
        totalPanel.validate();
        totalPanel.repaint();
    }

    public JPanel panel() {
        return totalPanel;
    }

    private void determineAndApplyEmptyRows() {
        int emptyCount = 0;
        LogEntryRowPanel lastEmptyRowPanel = null;
        for (LogEntryRowPanel rowPanel : logEntryRowPanels) {
            if (rowPanel.isEmpty()) {
                emptyCount++;
                lastEmptyRowPanel = rowPanel;
            }
        }
        if (emptyCount == 3) {
            return;
        } else if (emptyCount < 3) {
            addLogEntryRowPanel(new TlLogEntryRow("", "", "", ""));
            redraw();
            determineAndApplyEmptyRows();
        } else {
            lastEmptyRowPanel.deleteObserver(this);
            // Remove the row-panel from the list
            logEntryRowPanels.remove(lastEmptyRowPanel);
            // Remove the row-panel from the parent panel
            entryPanel.remove(lastEmptyRowPanel.panel());
            redraw();
            determineAndApplyEmptyRows();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LogEntryRowPanel) {
            determineAndApplyEmptyRows();
            persistedLogs.save(logs());
        }
    }

    public Logs logs() {
        List<Log> logs = new ArrayList<>();
        for (LogEntryRowPanel panel : logEntryRowPanels) {
            logs.add(panel.log());
        }
        return new DefaultLogs(logs);
    }

    public void clearLogEntryRows() {
        for (LogEntryRowPanel logEntryRowPanel : logEntryRowPanels) {
            logEntryRowPanel.clearAll();
        }
        determineAndApplyEmptyRows();
        persistedLogs.save(logs());
    }
}