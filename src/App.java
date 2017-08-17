import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
public class App implements Runnable {
    public static String systemEOL = System.getProperty("line.separator");
    private OptionsPanel optionsPanel = new OptionsPanel();
    private TitlePanel titlePanel = new TitlePanel();
    private LogsEntryPanel logsEntryPanel = new LogsEntryPanel();
    private MainFrame mainFrame;
    @Override
    public void run() {
        mainFrame = new MainFrame();
        new Timer(true).scheduleAtFixedRate(
            new BackupSave(), 0, 300_000
        );
    }
    private class BackupSave extends TimerTask {
        @Override
        public void run() {
            try {
                FileWriter fw = new FileWriter(
                    LocalDate.now().toString() + ".backup.txt",
                    true
                );
                fw.append(new Report(logsEntryPanel.entryRows()).toString());
                fw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    mainFrame.frame(), "Autosave failed. " + e.getMessage()
                );
            }
        }
    }
    private class MainFrame {
        private JFrame frame = new JFrame("TiliaLog");
        public MainFrame() {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(925, 525));
            frame.add(logsEntryPanel.panel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        public JFrame frame() {
            return frame;
        }
        public void redraw() {
            frame.validate();
            frame.repaint();
        }
    }
    private class LogsEntryPanel {
        private JPanel totalPanel = new JPanel(new BorderLayout());
        private JPanel entryPanel = new JPanel();
        private JScrollPane scrollPanel = new JScrollPane(
            entryPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        private List<EntryRow> entryRowPanels = new ArrayList<>();
        private int ENTRY_ROW_PANEL_STARTING_COUNT = 10;
        public LogsEntryPanel() {
            entryPanel.setLayout(new FlowLayout());
            entryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            entryPanel.add(titlePanel.panel());
            for (int i = 1; i <= ENTRY_ROW_PANEL_STARTING_COUNT; i++) {
                addEntryRowPanel();
            }
            totalPanel.add(optionsPanel.panel(), BorderLayout.NORTH);
            totalPanel.add(scrollPanel, BorderLayout.CENTER);
        }
        public JPanel panel() {
            return totalPanel;
        }
        public List<EntryRow> entryRows() {
            return entryRowPanels;
        }
        public void addEntryRowPanel() {
            EntryRowPanel entryRowPanel = new EntryRowPanel();
            entryRowPanels.add(entryRowPanel);
            entryPanel.add(entryRowPanel.panel());
            entryPanel.setPreferredSize(
                new Dimension(840, entryPanel.getComponentCount() * 40)
            );
        }
    }
    private class TitlePanel {
        private JPanel panel = new JPanel();
        public TitlePanel() {
            panel.setPreferredSize(new Dimension(800, 30));
            panel.setLayout(new FlowLayout());

            JLabel storyLabel = new JLabel("Story");
            storyLabel.setPreferredSize(new Dimension(100, 25));
            panel.add(storyLabel);

            JLabel startedAtLabel = new JLabel("Started at");
            startedAtLabel.setPreferredSize(new Dimension(100, 25));
            panel.add(startedAtLabel);

            JLabel endedAtLabel = new JLabel("Ended at");
            endedAtLabel.setPreferredSize(new Dimension(100, 25));
            panel.add(endedAtLabel);

            JLabel descriptionLabel = new JLabel("Description");
            descriptionLabel.setPreferredSize(new Dimension(300, 25));
            panel.add(descriptionLabel);

            JLabel actionsLabel = new JLabel("Actions");
            actionsLabel.setPreferredSize(new Dimension(100, 25));
            panel.add(actionsLabel);
        }
        public JPanel panel() {
            return panel;
        }
    }
    private class OptionsPanel {
        private JPanel panel = new JPanel();
        private JCheckBox combineStoriesCheckBox = new JCheckBox("Combine stories");
        private JLabel roundStampToLabel = new JLabel("Round stamp to:");
        private JTextField roundStampToField = new JTextField("1", 3);
        private JButton generateReport = new JButton("Generate report");
        private JButton addEntryRow = new JButton("Add row");
        public OptionsPanel() {
            panel.setPreferredSize(new Dimension(800, 40));
            panel.add(combineStoriesCheckBox);
            panel.add(roundStampToLabel);
            panel.add(roundStampToField);
            addEntryRow.addActionListener(new AddEntryRowActionListener());
            panel.add(addEntryRow);
            generateReport.addActionListener(new GenerateReportActionListener());
            panel.add(generateReport);
        }
        public JPanel panel() {
            return panel;
        }
        private Boolean combineStoriesIsSelected() {
            return combineStoriesCheckBox.isSelected();
        }
        private int roundStampToChoice() {
            try {
                return Integer.valueOf(roundStampToField.getText());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Unable to interpret as a number: "
                    + roundStampToField.getText()
                );
            }
        }
        private class GenerateReportActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (combineStoriesIsSelected()) {
                    try {
                        JOptionPane.showMessageDialog(
                            panel,
                            new JTextArea(
                                new Report(logsEntryPanel.entryRows())
                                    .combinedAsString(),
                                20,
                                50
                            )
                        );
                    } catch (IllegalArgumentException|IllegalStateException ex) {
                        JOptionPane.showMessageDialog(
                            panel,
                            ex.getMessage()
                        );
                    }
                }

                if (! combineStoriesIsSelected()) {
                    try {
                        JOptionPane.showMessageDialog(
                            panel,
                            new JTextArea(
                                new Report(logsEntryPanel.entryRows())
                                    .regularAsString(),
                                20,
                                50
                            )
                        );
                    } catch (IllegalArgumentException|IllegalStateException ex) {
                        JOptionPane.showMessageDialog(
                            panel,
                            ex.getMessage()
                        );
                    }
                }
            }
        }
        private class AddEntryRowActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                logsEntryPanel.addEntryRowPanel();
                mainFrame.redraw();
            }
        }
    }
    private class EntryRowPanel implements EntryRow {
        private JPanel panel = new JPanel();
        private JTextField storyField = new JTextField();
        private JTextField startedAtField = new JTextField();
        private JTextField endedAtField = new JTextField();
        private JTextField descriptionField = new JTextField();
        private JButton stampTime = new JButton("Stamp");
        public int PANEL_HEIGHT = 30;
        public EntryRowPanel() {
            panel.setPreferredSize(new Dimension(800, PANEL_HEIGHT));
            panel.setLayout(new FlowLayout());

            storyField.setPreferredSize(new Dimension(100, 25));
            panel.add(storyField);

            startedAtField.setPreferredSize(new Dimension(100, 25));
            panel.add(startedAtField);

            endedAtField.setPreferredSize(new Dimension(100, 25));
            panel.add(endedAtField);

            descriptionField.setPreferredSize(new Dimension(300, 25));
            panel.add(descriptionField);

            stampTime.setToolTipText(
                "Enters the current time to the next available field"
            );
            stampTime.addActionListener(new stampTimeActionListener());

            panel.add(stampTime);
        }
        private class stampTimeActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String now;
                try {
                    now = new Clock(LocalTime.now())
                        .asStringRounded(
                            optionsPanel.roundStampToChoice()
                        )
                    ;
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(panel,
                        e.getMessage()
                    );
                    return;
                }
                if (startedAt().length() == 0) {
                    startedAtField.setText(now);
                } else if (endedAt().length() == 0) {
                    endedAtField.setText(now);
                } else {
                    JOptionPane.showMessageDialog(panel,
                        "No available field to enter current time to."
                    );
                }
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
            return
                story().length() == 0
                && startedAt().length() == 0
                && endedAt().length() == 0
            ;
        }
        public String toString() {
            return new StringBuilder()
                .append(story()).append("\t")
                .append(startedAt()).append("\t")
                .append(endedAt()).append("\t")
                .append(description())
                .append(systemEOL)
                .toString()
            ;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }
}
