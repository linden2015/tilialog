package com.tilialog.ui;

import java.net.URI;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class AboutPanel {

    private final String siteUrl = "https://github.com/linden2015/tilialog";

    private JPanel panel;

    public AboutPanel() {
        buildUI();
    }

    public JPanel panel() {
        return panel;
    }

    private void buildUI() {
        panel = new JPanel();
        JEditorPane editorPane = new JEditorPane();
        JLabel titleLabel = new JLabel("Project site:");
        panel.add(titleLabel);
        JLabel urlLabel = new JLabel("<html><u>" + siteUrl + "</u></html>");
        urlLabel.addMouseListener(new UrlLabelMouseListener());
        panel.add(urlLabel);
    }

    private class UrlLabelMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent me) {
            try {
                Desktop.getDesktop().browse(
                    URI.create(siteUrl)
                );
            } catch (IOException e) {
                throw new RuntimeException(
                    "Unrecoverable error while opening project webpage.", e
                );
            }
        }
    }
}
