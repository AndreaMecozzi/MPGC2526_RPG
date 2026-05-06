package it.unicam.cs.mpgc.rpg126225.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoadingScreen extends BorderPane implements Screen{
    private final Font pressStart2p=
            Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 30);

    private ProgressBar progressBar;

    public LoadingScreen(Parent nextScreen) {
        this.setStyle("-fx-background-color: black;");
        inizializza();
        applicaStileRetro();
        avviaAnimazione(nextScreen);
    }

    @Override
    public void inizializza() {
        Label testo=new Label("Caricamento...");
        testo.setFont(pressStart2p);
        testo.setStyle("-fx-text-fill: white;");

        this.progressBar=new ProgressBar();
        this.progressBar.setProgress(0);
        this.progressBar.setMaxWidth(600);
        this.progressBar.setPrefHeight(30);


        VBox vbox=new VBox(10);
        vbox.getChildren().addAll(testo,this.progressBar);
        vbox.setMaxWidth(600);
        vbox.setAlignment(Pos.CENTER_LEFT);

        this.setCenter(vbox);
    }

    private void applicaStileRetro() {
        // Definiamo il CSS come una stringa leggibile
        String css =
                ".progress-bar .track {" +
                        "    -fx-background-color: white, black;" + // Bordo bianco, fondo nero
                        "    -fx-background-insets: 0, 3;" +       // Spessore bordo 3px
                        "    -fx-background-radius: 0;" +
                        "}" +
                        ".progress-bar .bar {" +
                        "    -fx-background-color: white;" +       // Colore caricamento
                        "    -fx-background-insets: 8;" +          // Padding nero interno di 5px (8-3)
                        "    -fx-background-radius: 0;" +
                        "}";

        // Questo comando "inietta" il CSS direttamente nel pannello
        this.getStylesheets().add("data:text/css," + css.replace(" ", "%20"));
    }

    private void avviaAnimazione(Parent nextScreen) {
        // Timeline: ogni 100 millisecondi aumenta un pochino il progresso
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> {
                    double attuale = progressBar.getProgress();
                    if (attuale < 1.0) {
                        progressBar.setProgress(attuale + 0.01); // Aumenta dell'1%
                    }
                })
        );

        timeline.setCycleCount(100); // Ripeti 100 volte (100 * 1% = 100%)
        timeline.play();

        // Opzionale: cosa fare quando finisce?
        timeline.setOnFinished(event -> passaSchermataSuccessiva(nextScreen));
    }

    public void passaSchermataSuccessiva(Parent nextScreen) {
        this.getScene().setRoot(nextScreen);
        if(nextScreen instanceof Musicabile){
            ((Musicabile) nextScreen).avviaMusica();
        }
    }
}
