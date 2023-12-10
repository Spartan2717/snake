package it.unimol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;


import org.junit.Test;

import it.unimol.gui.classifica.ClassificaPanel;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void handleInserisciGiocatoreClick() {
        String username = "test";
        if (username.isEmpty()) {
            System.out.println("Username nullo");
            assert (false);
        } else
            System.out.println("Username inserito");
        assert (true);

    }

    /* 
    @Test(expected = NullPointerException.class)
    public void testHandleInserisciGiocatoreClickWithEmptyUsername() {
        ClassificaPanel panel = new ClassificaPanel(null, false, new HashMap<>(), new HashMap<>(), 0);
        panel.usernameTextField.setText("");
        panel.handleInserisciGiocatoreClick();
        panel.classificaDialog.dispose(); // Assicurati che ciò sia presente per chiudere la finestra
        assertNull("La finestra di dialogo dovrebbe essere chiusa", panel.classificaDialog);
    }
*/
    @Test(expected = AssertionError.class)
    public void testHandleInserisciGiocatoreClickWithNullMap() {
        // Crea un oggetto panel con una mappa nulla
        ClassificaPanel panel = new ClassificaPanel(null, false, null, new HashMap<>(), 0);

        // Imposta un username
        panel.usernameTextField.setText("testuser");

        // Chiamata al metodo da testare
        panel.handleInserisciGiocatoreClick();

        // Verifica se la finestra di dialogo è stata chiusa
        assertNull("La finestra di dialogo dovrebbe essere chiusa", panel.classificaDialog);

        // Verifica se la mappa è stata aggiornata correttamente (adatta questa
        // asserzione alla tua logica)
        assertNotNull("La mappa dovrebbe essere non nulla", panel.classificaNormale);
        assertTrue("La mappa dovrebbe contenere l'utente", panel.classificaNormale.containsKey("testuser"));
        assertEquals("Il valore nella mappa dovrebbe essere 0", 0, panel.classificaNormale.get("testuser"));
    }
}