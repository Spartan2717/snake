package it.unimol.gui.classifica;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ClassificaPanel extends JPanel {

    private static final String FILENAMECN = "classificaNormale.bin";
    private static final String FILENAMECD = "classificaDifficile.bin";
    private Boolean difficile;
    public Map classificaNormale;
	private Map classificaDifficile;
    private int appleEaten;
    public JTextField usernameTextField;
    private JButton classificatiButton;
    public JDialog classificaDialog;

    public ClassificaPanel( ClassificaDialog classificaDialog, Boolean difficile, Map classificaNormale, Map classificaDifficile, int appleEaten) {
        this.classificaDialog = classificaDialog;
        this.difficile = difficile;
        this.classificaNormale = classificaNormale;
        this.classificaDifficile = classificaDifficile;
        this.appleEaten = appleEaten;
        this.initializeUI();
    }

    private void initializeUI() {
        this.setLayout(new GridLayout(2, 1));

        this.usernameTextField = new JTextField();
        this.classificatiButton = new JButton("Inserisci username");

        this.add(this.usernameTextField);
        this.add(this.classificatiButton);

        this.classificatiButton.addActionListener(e -> this.handleInserisciGiocatoreClick());

    }

    public void handleInserisciGiocatoreClick() {

        String username = usernameTextField.getText();

        if(username.isEmpty()){
            JOptionPane.showMessageDialog(null, "Username non può essere vuoto");
        }
        else {
            if (difficile) {
                assert username != null : "I'username dato in input non può essere null";
                assert this.classificaDifficile != null : "La mappa non può essere null";

                this.classificaDifficile.put(username, appleEaten);

                try (
                        FileOutputStream fos = new FileOutputStream(FILENAMECD);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos)
                ) {
                    objectOutputStream.writeObject(this.classificaDifficile);
                } catch (IOException e) {
                    this.classificaDifficile.remove(username);
                }

            } else {
                assert username != null : "I'username dato in input non può essere null";
                assert this.classificaNormale != null : "La mappa non può essere null";

                this.classificaNormale.put(username, appleEaten);

                try (
                        FileOutputStream fos = new FileOutputStream(FILENAMECN);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos)
                ) {
                    objectOutputStream.writeObject(this.classificaNormale);
                } catch (IOException e) {
                    this.classificaNormale.remove(username);
                }
            }
            classificaDialog.dispose();
        }



    }
}
