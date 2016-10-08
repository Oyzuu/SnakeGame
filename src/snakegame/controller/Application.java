package snakegame.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Application extends JFrame {
    private Container DISPLAY;
    private Playground playground;

    Application(String name) {
        setTitle(name);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        DISPLAY = getContentPane();

        playground = new Playground();
        DISPLAY.add(playground);

        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                playground.requestFocus();
            }
        });

        pack();
        setVisible(true);
    }
}
