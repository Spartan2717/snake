package it.unimol.gui.main;

import it.unimol.gui.game.GameDialog;
import it.unimol.gui.game.GamePanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe che rappresenta il Panel del menu principale con le impostazioni di gioco.
 *
 * @author Gabriele Pontevolpe
 * @version 1.0
 */

public class MenuPanel extends JPanel implements ActionListener {
    private static final String FILENAMECN = "classificaNormale.bin";
    private static final String FILENAMECD = "classificaDifficile.bin";

    private String[] opzioni = {"Start", "Classifica", "Impostazioni", "Quit"};
    private String[] impostazioni = {"Modalità: ", "Difficile", "Arcobaleno", "Return"};
    private int currentSelection = 0;

    private boolean menu = true;
    private boolean arcobaleno = false, difficile = false;

    private JFrame parent;
    private Map<String, Integer> classificaNormale;
    private Map<String, Integer> classificaDifficile;

    private GameDialog gameDialog;
    private JFrame menuFrame;

    /**
     * Se esiste il file con il menu salvato precedentemente, restituisce tale menu.
     * Altrimenti, restituisce un menu vuoto.
     *
     * @throws IOException Se esiste il file di salvataggio, ma ci sono problemi nella lettura.
     */
    public MenuPanel(JFrame menuFrame) {
        this.menuFrame = menuFrame;

        //Serializzazione classifica normale
        File fileCN = new File(FILENAMECN);
        if (fileCN.exists()) {
            try (
                    FileInputStream fis = new FileInputStream(FILENAMECN);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fis)
            ) {
                this.classificaNormale = (Map) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                assert false : "Non dovrebbe essere lanciata una ClassNotFoundException";
                assert false : "Non dovrebbe essere lanciata una IOException";
            }
        } else {
            this.classificaNormale = new HashMap<>();
        }

        //Serializzazione classifica difficile
        File fileCD = new File(FILENAMECD);
        if (fileCD.exists()) {
            try (
                    FileInputStream fis = new FileInputStream(FILENAMECD);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fis)
            ) {
                this.classificaDifficile = (Map) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                assert false : "Non dovrebbe essere lanciata una ClassNotFoundException";
                assert false : "Non dovrebbe essere lanciata una IOException";
            }
        } else {
            this.classificaDifficile = new HashMap<>();
        }

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MYKeyAdapter());
        menu = true;
        this.parent = (JFrame) SwingUtilities.getWindowAncestor(this);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) {
        if (menu) {
            //Menu
            g.setColor(new Color(53, 50, 150));
            g.fillRect(0, 0, MenuFrame.getLARGHEZZA(), MenuFrame.getLARGHEZZA());

            for (int i = 0; i < opzioni.length; i++) {
                if (i == currentSelection) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor((Color.WHITE));
                }
                //Scritte menu
                g.setFont(new Font("Arial", Font.PLAIN, 48));
                g.drawString(opzioni[i], MenuFrame.getLARGHEZZA() / 2 - 100, MenuFrame.getLARGHEZZA() / 2 - 175 + i * 100);
            }
            repaint();
        } else {
            //Impostazioni
            g.setColor(new Color(53, 52, 153));
            g.fillRect(0, 0, MenuFrame.getLARGHEZZA(), MenuFrame.getLARGHEZZA());

            for (int i = 0; i < impostazioni.length; i++) {
                if (i == currentSelection) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor((Color.WHITE));
                }
                //Scritte impostazioni
                g.drawLine(MenuFrame.getLARGHEZZA() / 2, 0, MenuFrame.getLARGHEZZA() / 2, GamePanel.HEIGHT);
                g.setFont(new Font("Arial", Font.PLAIN, 48));
                g.drawString(impostazioni[i], MenuFrame.getLARGHEZZA() / 2 - 100, MenuFrame.getLARGHEZZA() / 2 - 175 + i * 100);
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }


    public class MYKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (menu) {
                //Menu switch
                switch (e.getKeyCode()) {
                    //Per salire
                    case KeyEvent.VK_DOWN:
                        currentSelection++;
                        if (currentSelection >= opzioni.length) {
                            currentSelection = 0;
                        }
                        break;
                    //Per scendere
                    case KeyEvent.VK_UP:
                        currentSelection--;
                        if (currentSelection < 0) {
                            currentSelection = opzioni.length - 1;
                        }
                        break;
                    //Per entrare
                    case KeyEvent.VK_ENTER:
                        if (currentSelection == 0) {
                            gameDialog = new GameDialog(menuFrame, difficile, arcobaleno, classificaNormale, classificaDifficile);
                            gameDialog.setVisible(true);
                            menuFrame.setVisible(false);
                        } else if (currentSelection == 1) {
                            //Classifica
                            JOptionPane.showMessageDialog(null, "Classifica normale \n" + classificaNormale.entrySet().stream()
                                    .sorted((c1, c2) -> Integer.compare(c2.getValue(), c1.getValue())).map(c -> c.toString()).reduce((agg, c) -> agg + "\n" + c).orElse(""));

                            JOptionPane.showMessageDialog(null, "Classifica difficile \n" + classificaDifficile.entrySet().stream()
                                    .sorted((c1, c2) -> Integer.compare(c2.getValue(), c1.getValue())).map(c -> c.toString()).reduce((agg, c) -> agg + "\n" + c).orElse(""));
                        } else if (currentSelection == 2) {
                            //Entra nelle impostazioni
                            menu = false;
                        } else if (currentSelection == 3) {
                            //Exit
                            System.exit(0);
                        }
                        break;
                }
            } else {
                //Impostazioni Switch
                switch (e.getKeyCode()) {
                    //Per scendere
                    case KeyEvent.VK_DOWN:
                        currentSelection++;
                        if (currentSelection >= impostazioni.length) {
                            currentSelection = 1;
                        }
                        break;
                        //Per salire
                    case KeyEvent.VK_UP:
                        currentSelection--;
                        if (currentSelection < 1) {
                            currentSelection = impostazioni.length - 1;
                        }
                        break;
                        //Per entrare
                    case KeyEvent.VK_ENTER:
                        if (currentSelection == 1) {
                            if (difficile) {
                                difficile = false;
                                impostazioni[1] = "Difficile";
                            } else {
                                difficile = true;
                                JOptionPane.showMessageDialog(null,"Nella modalità difficile dopo 10 mele raccolte non vedrai più il corpo ma ci sarà, fai attenzione!");
                                impostazioni[1] = "Difficile X";
                                arcobaleno = false;
                                impostazioni[2] = "Arcobaleno";
                            }
                        } else if (currentSelection == 2) {
                            if (arcobaleno) {
                                arcobaleno = false;
                                impostazioni[2] = "Arcobaleno";
                            } else {
                                arcobaleno = true;
                                impostazioni[2] = "Arcobaleno X";
                                difficile = false;
                                impostazioni[1] = "Difficile";
                            }
                        } else if (currentSelection == 3) {
                            menu = true;
                            currentSelection = 0;
                            break;
                        }
                }
            }
        }

    }
}
