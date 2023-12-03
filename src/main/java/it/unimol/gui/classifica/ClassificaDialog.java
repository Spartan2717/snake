package it.unimol.gui.classifica;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ClassificaDialog extends JDialog {
    private ClassificaPanel panel;

    public ClassificaDialog(JFrame parent, Boolean difficile, Map classificaNormale, Map classificaDifficile, int appleEaten) {
        super(parent, true);

        Dimension dimension = new Dimension(400, 200);

        this.setTitle("Inserisciti in classifica!");
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();

        //Centra il frame automaticamente
        this.setLocationRelativeTo(null);

        panel = new ClassificaPanel( this , difficile, classificaNormale, classificaDifficile, appleEaten);
        this.add(panel);

    }


}