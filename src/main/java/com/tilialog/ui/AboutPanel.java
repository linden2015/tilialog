package com.tilialog.ui;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    private class UrlLabelMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
            try {
                Desktop.getDesktop().browse(
                    java.net.URI.create(siteUrl)
                );
            } catch (IOException e) {
                throw new RuntimeException(
                    "Unrecoverable error while opening project site.", e
                );
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
