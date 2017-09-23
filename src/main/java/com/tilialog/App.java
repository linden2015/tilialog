package com.tilialog;

import com.github.zafarkhaja.semver.Version;
import com.jcabi.xml.XMLDocument;
import com.tilialog.ui.MainFrame;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingUtilities;

public class App implements Runnable {

    private MainFrame mainFrame;

    public App(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        mainFrame.setVisible();
    }

    public static void main(String[] args) throws IOException {
        AppIsOutdated appIsOutdated = new CachedAppIsOutdated(
            new TlAppIsOutdated(
                Version.valueOf(
                    new XMLDocument(
                        new TlTextFile(
                            new File("pom.xml")
                        ).readAll()
                    ).xpath("/*[local-name()=\"project\"]/*[local-name()=\"version\"]/text()").get(0)
                )
            )
        );

        MainFrame mainFrame = new MainFrame(
            new TlSettings(
                new TlTextFile(
                    new File("settings.list")
                )
            ),
            appIsOutdated
        );

        SwingUtilities.invokeLater(
            new App(
                mainFrame
            )
        );

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (appIsOutdated.asBoolean()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            mainFrame.showUpdatesMessageDialog();
                        }
                    });
                }
            }
        }).start();
    }
}
