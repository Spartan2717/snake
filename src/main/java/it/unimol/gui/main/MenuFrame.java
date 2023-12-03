package it.unimol.gui.main;


import javax.swing.*;
import java.awt.*;


public class MenuFrame extends JFrame {

    private static final int LARGHEZZA = 600;
    private static final int ALTEZZA = 600;
    private Dimension dimension;
    private MenuPanel panel;

    public static int getLARGHEZZA() {
        return LARGHEZZA;
    }

    public MenuFrame() {
        dimension = new Dimension(LARGHEZZA, ALTEZZA);

        this.setTitle("Menu");
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();

        //Centra il frame automaticamente
        this.setLocationRelativeTo(null);

        panel = new MenuPanel(this);
        this.add(panel);
    }

}
