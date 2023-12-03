package it.unimol.gui.game;


import it.unimol.gui.classifica.ClassificaDialog;
import it.unimol.player.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    //Costanti e variabili
    private static final int LARGHEZZA = 600;
    private static final int ALTEZZA = 600;
    private static final int UNIT_SIZE = 25;
    private static final int DELAY = 75;
    private Timer timer;
    private Random random;
    private Map classificaNormale, classificaDifficile;
    private JFrame parent;
    private JDialog gameDialog;
    private Snake snake;
    private int appleX;
    private int appleY;
    private boolean running = false;
    private boolean difficile;
    private boolean arcobaleno;
    private JFrame menuFrame;

    public GamePanel(JDialog gameDialog, JFrame menuFrame, Boolean difficile, Boolean arcobaleno, Map classificaNormale, Map classificaDifficile) {
        this.gameDialog = gameDialog;
        this.menuFrame = menuFrame;
        this.difficile = difficile;
        this.arcobaleno = arcobaleno;
        this.classificaNormale = classificaNormale;
        this.classificaDifficile = classificaDifficile;

        parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        random = new Random();
        this.setPreferredSize(new Dimension(LARGHEZZA, ALTEZZA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        startGame();
    }

    public void startGame() {
        newApple();   snake = new Snake(difficile, arcobaleno);
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void newApple() {
        appleX = random.nextInt(LARGHEZZA / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(ALTEZZA / UNIT_SIZE) * UNIT_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            //Mela
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            //Serpente
            snake.draw(g);
            punteggio(g);
        } else {
            gameOver(g);
            new ClassificaDialog(parent, difficile, classificaNormale, classificaDifficile, snake.getApplesEaten()).setVisible(true);
            gameDialog.dispose();
            menuFrame.setVisible(true);
        }
    }

    public void punteggio(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + snake.getApplesEaten(), (LARGHEZZA - metrics.stringWidth("Score: " + snake.getApplesEaten())) / 2, g.getFont().getSize());
    }

    public void checkApple() {
        if ((snake.getX() == appleX) && (snake.getY() == appleY)) {
            snake.addBodyParts();
            snake.addAppleEaten();
            newApple();
        }

    }

    public void move(){
        snake.move();
    }

    public void checkCollisions() {
        //controlla se la tersta di scotra con il corpo
       running = snake.checkCollisions();
        if (!running) {
            timer.stop();
        }


    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (LARGHEZZA - metrics.stringWidth("Game Over")) / 2, ALTEZZA / 2);
        punteggio(g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            snake.keyPressed(e);
        }
    }
}
