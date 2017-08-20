package com.tilialog.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel {
    private JPanel panel;

    public TitlePanel() {
        buildUI();
    }

    private void buildUI() {
        panel = new JPanel();
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
