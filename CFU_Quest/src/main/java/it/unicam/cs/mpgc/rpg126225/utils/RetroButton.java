package it.unicam.cs.mpgc.rpg126225.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * Bottone personalizzato in stile retrò (8-bit) per eliminare la duplicazione del codice
 * e centralizzare lo styling grafico e le animazioni dei bottoni nel gioco.
 *
 * @author Andrea Mecozzi
 */
public class RetroButton extends Button {

    private static final String STYLE_BASE = "-fx-background-color: black; -fx-border-color: white; -fx-text-fill: white;";
    private static final String STYLE_HOVER = "-fx-background-color: black; -fx-border-color: grey; -fx-text-fill: grey;";
    private static final String STYLE_PRESSED = "-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black;";

    /**
     * Costruisce un bottone con il testo specificato e dimensione del font di default (20px).
     *
     * @param text Il testo da visualizzare sul bottone.
     */
    public RetroButton(String text) {
        super(text);
        inizializza(20);
    }

    /**
     * Costruisce un bottone con il testo specificato e una dimensione del font personalizzata.
     *
     * @param text     Il testo da visualizzare sul bottone.
     * @param fontSize La dimensione del font in pixel.
     */
    public RetroButton(String text, double fontSize) {
        super(text);
        inizializza(fontSize);
    }

    /**
     * Inizializza lo stile, il font retrò e le animazioni di hover/click del bottone.
     */
    private void inizializza(double fontSize) {
        // Carica il font retrò VT323 dalle risorse
        try {
            Font vt323 = Font.loadFont(getClass().getResourceAsStream("/fonts/vt323.ttf"), fontSize);
            if (vt323 != null) {
                this.setFont(vt323);
            }
        } catch (Exception e) {
            System.err.println("Impossibile caricare il font vt323.ttf per RetroButton. Verrà usato il font di sistema.");
        }

        // Stile e allineamento base
        this.setStyle(STYLE_BASE);
        this.setAlignment(Pos.CENTER);
        this.setWrapText(true);

        // Gestione degli eventi di input (animazione stile 8-bit)
        this.setOnMousePressed(e -> this.setStyle(STYLE_PRESSED));
        this.setOnMouseReleased(e -> this.setStyle(STYLE_BASE));
        this.setOnMouseEntered(e -> this.setStyle(STYLE_HOVER));
        this.setOnMouseExited(e -> this.setStyle(STYLE_BASE));
    }
}
