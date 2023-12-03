package it.unimol.gui.game;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class GameDialog extends JDialog {

    private static final int LARGHEZZA = 619;
    private static final int ALTEZZA = 644;
    private Dimension dimension;
    private GamePanel panel;


    public GameDialog(JFrame menuFrame, Boolean difficile, Boolean arcobaleno, Map classificaNormale, Map classificadifficile) {
        dimension = new Dimension(LARGHEZZA, ALTEZZA);

        this.setTitle("Snake");
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();

        //Centra il frame automaticamente
        this.setLocationRelativeTo(null);

        panel = new GamePanel(this, menuFrame, difficile, arcobaleno, classificaNormale, classificadifficile);
        this.add(panel);
    }

}
