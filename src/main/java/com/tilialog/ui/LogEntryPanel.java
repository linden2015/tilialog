package com.tilialog.ui;

import com.tilialog.LogEntryRow;
import com.tilialog.PersistedLogEntryRows;
import com.tilialog.Settings;
import com.tilialog.TlLogEntryRow;
import com.tilialog.TlTextFile;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

    private PersistedLogEntryRows persistedLogEntryRows;

    public LogEntryPanel(Settings settings) {
        this.settings = settings;
        persistedLogEntryRows = new PersistedLogEntryRows(
            new TlTextFile(
                new File("log-entry-rows.list")
            ),
            ";;;"
        );
        buildUI();
    }

    private void buildUI() {
        entryPanel = new JPanel();
        entryPanel.setLayout(new FlowLayout());
        entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        for (LogEntryRow row : persistedLogEntryRows.load()) {
            addLogEntryRowPanel(row);
        }
        scrollPanel = new JScrollPane(
            entryPanel,
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
            if (rowPanel.logEntryRow().isEmpty()) {
                emptyCount++;
                lastEmptyRowPanel = rowPanel;
            }
        }
        if (emptyCount < 3) {
            addLogEntryRowPanel(new TlLogEntryRow("", "", "", ""));
            redraw();
            determineAndApplyEmptyRows();
        }
        if (emptyCount > 3) {
            lastEmptyRowPanel.deleteObserver(this);
            logEntryRowPanels.remove(lastEmptyRowPanel);
            entryPanel.remove(lastEmptyRowPanel.panel());
            redraw();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LogEntryRowPanel) {
            determineAndApplyEmptyRows();
            persistedLogEntryRows.save(logEntryRows());
        }
    }

    public List<LogEntryRow> logEntryRows() {
        List<LogEntryRow> rows = new ArrayList<>();
        for (LogEntryRowPanel panel : logEntryRowPanels) {
            rows.add(panel.logEntryRow());
        }
        return rows;
    }

    public void clearLogEntryRows() {
        for (LogEntryRowPanel logEntryRowPanel : logEntryRowPanels){
            logEntryRowPanel.clearAll();
        }
        determineAndApplyEmptyRows();
        persistedLogEntryRows.save(logEntryRows());
    }
}