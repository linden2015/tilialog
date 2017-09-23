package com.tilialog.ui;

import com.tilialog.AppIsOutdated;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UpdatesPanel {

    public static String EOL = System.getProperty("line.separator");

    private final String releasesUrl = "https://github.com/linden2015/tilialog/releases";

    private JPanel panel;

    private JLabel result;

    private AppIsOutdated isOutdated;

    public UpdatesPanel(AppIsOutdated isOutdated) {
        this.isOutdated = isOutdated;
        buildUI();
    }

    public JPanel panel() {
        return panel;
    }

    private void buildUI() {
        result = new JLabel();

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(640, 120));
        panel.add(result);

        if (isOutdated.asBoolean()) {
            result.setText(
                new StringBuilder("You are not on the latest version.")
                    .append(" Your version is: ")
                    .append(isOutdated.runningVersion().toString())
                    .toString()
            );

            JButton openReleasesButton = new JButton("Open releases page");
            openReleasesButton.addMouseListener(new OpenReleasesButtonListener());
            panel.add(openReleasesButton);
        } else {
            result.setText("You are on the latest version.");
        }
    }

    private class OpenReleasesButtonListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent me) {
            try {
                Desktop.getDesktop().browse(
                    URI.create(releasesUrl)
                );
            } catch (IOException e) {
                throw new RuntimeException(
                    "Unrecoverable error while opening releases webpage.", e
                );
            }
        }
    }
}
