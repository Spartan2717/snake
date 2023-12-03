package it.unimol.player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Snake {
    private static final int LARGHEZZA = 600;
    private static final int ALTEZZA = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (LARGHEZZA * ALTEZZA) / UNIT_SIZE;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    private int bodyParts = 6;
    private int applesEaten;
    private boolean difficile;
    private boolean arcobaleno;
    private Random random = new Random();
    private char direction = 'R';

    public Snake(Boolean difficile, Boolean arcobaleno){
        this.difficile = difficile;
        this.arcobaleno = arcobaleno;
}



    public void draw(Graphics g){
        //Corpo del serpente
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                if (difficile) {
                    if (applesEaten / 10 == 1) {
                        g.setColor(new Color(0, 0, 0));
                    } else {
                        g.setColor(new Color(58, 255, 0));
                    }
                } else {
                    g.setColor(new Color(58, 255, 0));
                }
                if (arcobaleno) {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void moveLeft(){
        x[0] = x[0] - UNIT_SIZE;
    }

    public void moveRight(){
        x[0] = x[0] + UNIT_SIZE;
    }

    public void moveUp(){
        y[0] = y[0] - UNIT_SIZE;
    }

    public void moveDown(){
        y[0] = y[0] + UNIT_SIZE;
    }

    public boolean checkCollisions() {
        //controlla se la testa di scontra con il corpo
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                return false;
            }
        }
        //controlla se la testa di scontra con il bordo sinistro
        if (x[0] < 0) {
            return false;
        }
        //controlla se la testa di scontra con il bordo destro
        if (x[0] > LARGHEZZA) {
            return false;
        }
        //controlla se la testa di scontra sopra
        if (y[0] < 0) {
            return false;
        }
        //controlla se la testa di scontra sotto
        if (y[0] > ALTEZZA) {
            return false;
        }
        return true;
    }

    public int getX() {
        return x[0];
    }

    public int getY() {
        return y[0];
    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
        }
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                moveUp();
                break;
            case 'D':
                moveDown();
                break;
            case 'L':
                moveLeft();
                break;
            case 'R':
                moveRight();
                break;
        }
    }

    public void addBodyParts(){
        bodyParts++;
    }

    public void addAppleEaten(){applesEaten++;
    }

    public int getApplesEaten() {
        return applesEaten;
    }
}