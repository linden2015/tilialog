import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TimerTask;
public class App implements Runnable {
    private JCheckBox combineStories;
    private java.util.List<java.util.List<JTextField>> entries = new ArrayList<>();
    private JFrame frame;
    @Override
    public void run() {
        frame = appFrame();
        new java.util.Timer().scheduleAtFixedRate(new BackupSave(), 0, 300_000);
    }
    private class BackupSave extends TimerTask {
        @Override
        public void run() {
            try {
                FileWriter fw = new FileWriter(
                    LocalDate.now().toString() + ".backup.txt", true
                );
                fw.append(new Report(entries).toString());
                fw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    frame, "Autosave failed. " + e.getMessage()
                );
            }
        }
    }
    private JFrame appFrame() {
        JFrame frame = new JFrame("TiliaLog");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(logsEntryPanel());
        frame.setResizable(false);
        frame.setSize(new Dimension(650, 550));
        frame.setLocationRelativeTo(null);
        return frame;
    }
    private JPanel logsEntryPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setLayout(new FlowLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(titlePanel());
        for (int i = 1; i <= 15; i++) {
            panel.add(entryPanel());
        }
        panel.add(entryPanel());
        panel.add(optionsPanel());
        return panel;
    }
    private JPanel optionsPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 40));
        combineStories = new JCheckBox("Combine stories");
        panel.add(combineStories);
        JButton generateReport = new JButton("Generate report");
        generateReport.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (combineStories.isSelected()) {
                        JOptionPane.showMessageDialog(
                            panel,
                            "Maybe in next release." +
                                "Try without combining stories for now."
                        );
                    } else {
                        try {
                            JOptionPane.showMessageDialog(
                                panel, new Report(entries).regularAsString()
                            );
                        } catch (IllegalArgumentException | IllegalStateException ex) {
                            JOptionPane.showMessageDialog(panel, ex.getMessage());
                        }
                    }
                }
            }
        );
        panel.add(generateReport);
        return panel;
    }
    private JPanel titlePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 20));
        panel.setLayout(new GridLayout(0, 3, 20, 20));
        panel.add(new JLabel("Story"));
        panel.add(new JLabel("Started at"));
        panel.add(new JLabel("Ended at"));
        return panel;
    }
    private JPanel entryPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 20));
        panel.setLayout(new GridLayout(0, 3, 20, 20));

        JTextField storyField = new JTextField();
        panel.add(storyField);

        JTextField startedAtField = new JTextField();
        panel.add(startedAtField);

        JTextField endedAtField = new JTextField();
        panel.add(endedAtField);

        ArrayList<JTextField> entry = new ArrayList<>();
        entry.add(storyField);
        entry.add(startedAtField);
        entry.add(endedAtField);
        entries.add(entry);

        return panel;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }
}
