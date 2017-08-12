import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
public class App implements Runnable {
    private OptionsPanel optionsPanel = new OptionsPanel();
    private TitlePanel titlePanel = new TitlePanel();
    private LogsEntryPanel logsEntryPanel = new LogsEntryPanel();
    private MainFrame mainFrame;
    @Override
    public void run() {
        mainFrame = new MainFrame();
        new java.util.Timer().scheduleAtFixedRate(new BackupSave(), 0, 300_000);
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
            frame.setResizable(false);
            frame.setSize(new Dimension(650, 550));
            frame.add(logsEntryPanel.panel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        public JFrame frame() {
            return frame;
        }
    }
    private class LogsEntryPanel {
        private JPanel panel = new JPanel();
        private List<EntryRow> entryRowPanels = new ArrayList<>();
        public LogsEntryPanel() {
            panel.setPreferredSize(new Dimension(500, 500));
            panel.setLayout(new FlowLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.add(titlePanel.panel());
            for (int i = 1; i <= 15; i++) {
                EntryRowPanel entryRowPanel = new EntryRowPanel();
                entryRowPanels.add(entryRowPanel);
                panel.add(entryRowPanel.panel());
            }
            panel.add(optionsPanel.panel());
        }
        public JPanel panel() {
            return panel;
        }
        public List<EntryRow> entryRows() {
            return entryRowPanels;
        }
    }
    private class TitlePanel {
        private JPanel panel = new JPanel();
        public TitlePanel() {
            panel.setPreferredSize(new Dimension(500, 20));
            panel.setLayout(new GridLayout(0, 4, 20, 20));
            panel.add(new JLabel("Story"));
            panel.add(new JLabel("Started at"));
            panel.add(new JLabel("Ended at"));
            panel.add(new JLabel("Actions"));
        }
        public JPanel panel() {
            return panel;
        }
    }
    private class OptionsPanel {
        private JPanel panel = new JPanel();
        private JCheckBox combineStoriesCheckBox = new JCheckBox("Combine stories");
        private JButton generateReport = new JButton("Generate report");
        public OptionsPanel() {
            panel.setPreferredSize(new Dimension(500, 40));
            panel.add(combineStoriesCheckBox);
            generateReport.addActionListener(new GenerateReportActionListener());
            panel.add(generateReport);
        }
        public JPanel panel() {
            return panel;
        }
        private Boolean combineStoriesIsSelected() {
            return combineStoriesCheckBox.isSelected();
        }
        private class GenerateReportActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (combineStoriesIsSelected()) {
                    JOptionPane.showMessageDialog(panel,
                        "Maybe in next release."
                        + "Try without combining stories for now."
                    );
                }

                if (! combineStoriesIsSelected()) {
                    try {
                        JOptionPane.showMessageDialog(
                            panel,
                            new Report(logsEntryPanel.entryRows()).regularAsString()
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
    }
    private class EntryRowPanel implements EntryRow {
        private JPanel panel = new JPanel();
        private JTextField storyField = new JTextField();
        private JTextField startedAtField = new JTextField();
        private JTextField endedAtField = new JTextField();
        private JButton stampTime = new JButton("Stamp");
        public EntryRowPanel() {
            panel.setPreferredSize(new Dimension(500, 20));
            panel.setLayout(new GridLayout(0, 4, 20, 20));
            panel.add(storyField);
            panel.add(startedAtField);
            panel.add(endedAtField);
            stampTime.setToolTipText(
                "Enters the current time to the next available field"
            );
            stampTime.addActionListener(new stampTimeActionListener());
            panel.add(stampTime);
        }
        private class stampTimeActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
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
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }
}
