package com.tilialog;

import com.tilialog.ui.MainFrame;
import javax.swing.SwingUtilities;
import java.io.File;

public class App implements Runnable {
    private MainFrame mainFrame;
    private Settings settings;

    public App(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void run() {
        mainFrame = new MainFrame(this.settings);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            new App(
                new Settings(
                    new File("settings.ser")
                )
            )
        );
    }
}
