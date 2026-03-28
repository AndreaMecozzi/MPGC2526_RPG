package it.unicam.cs.mpgc.rpg126225.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;


public class MenuScreen extends Pane implements Musicabile{
    private final Font pressStart2pTitle=
            Font.loadFont(getClass().getResourceAsStream("/fonts/pressStart2p.ttf"), 60);

    private final Font vt323button=
            Font.loadFont(getClass().getResourceAsStream("/fonts/vt323.ttf"), 30);

    private MediaPlayer musicaMenu;

    public MenuScreen() {
        this.setStyle("-fx-background-color: black;");
        inizializzaMusica();
        inizializza();
    }

    public void inizializzaMusica() {
        String path=this.getClass().getResource("/audio/Kingdom_Awaits.mp3").toExternalForm();
        Media musica=new Media(path);
        musicaMenu=new MediaPlayer(musica);
        musicaMenu.setCycleCount(MediaPlayer.INDEFINITE);
        musicaMenu.setVolume(0.5);
    }

    @Override
    public void avviaMusica() {
        musicaMenu.play();
    }

    public void inizializza(){
        /// Creazione del titolo del Menu
        // Bottone
        ImageView logoUnicam=new ImageView();
        Image logo=new Image(getClass().getResourceAsStream("/images/logo.png"));
        logoUnicam.setImage(logo);
        logoUnicam.setPreserveRatio(true);
        logoUnicam.setFitWidth(150);
        logoUnicam.setLayoutX(325);
        logoUnicam.setLayoutY(25);

        // Titolo
        Label titolo=new Label("CFU QUEST");
        titolo.setFont(pressStart2pTitle);
        titolo.setStyle("-fx-text-fill: white;"+"-fx-background-color: black;");

        HBox containerTitle=new HBox(35);
        containerTitle.getChildren().addAll(logoUnicam, titolo);
        containerTitle.setLayoutX(25);
        containerTitle.setLayoutY(25);
        containerTitle.setAlignment(Pos.CENTER_LEFT);

        /// Creazione dei bottoni del menu
        Button newGame=creaBottone("Nuova partita");
        newGame.setOnAction(e -> goGameScreen());

        Button loadGame=creaBottone("Carica partita");

        Button exit=creaBottone("Esci");
        exit.setOnAction(e->System.exit(0)); // "Torna al desktop"

        VBox containerButtons=new VBox(20);
        containerButtons.getChildren().addAll(newGame,loadGame,exit);
        containerButtons.setAlignment(Pos.CENTER);
        containerButtons.setPrefSize(300,300);
        containerButtons.setLayoutX(250);
        containerButtons.setLayoutY(200);

        this.getChildren().addAll(containerTitle, containerButtons);
    }

    public Button creaBottone(String testo){
        //Struttura standard
        Button button=new Button(testo);
        button.setFont(vt323button);
        button.setStyle("-fx-background-color: black;"+
                "-fx-border-color: white;"+
                "-fx-text-fill: white;");
        button.setPrefSize(275,60);

        // Animazione quando si preme il tasto
        button.setOnMousePressed(e->button.setStyle("-fx-background-color: white;"+
                "-fx-border-color: black;"+
                "-fx-text-fill: black;"));
        button.setOnMouseReleased(e->button.setStyle("-fx-background-color: black;"+
                "-fx-border-color: white;"+
                "-fx-text-fill: white;"));

        // Animazione quando si passa sopra il bottone
        button.setOnMouseEntered(e->button.setStyle("-fx-background-color: black;"+
                "-fx-border-color: grey;"+
                "-fx-text-fill: grey;"));
        button.setOnMouseExited(e->button.setStyle("-fx-background-color: black;"+
                "-fx-border-color: white;"+
                "-fx-text-fill: white;"));

        return button;
    }

    public void goGameScreen(){
        musicaMenu.stop();
        GameScreen gameScreen=new GameScreen();
        LoadingScreen loadingScreen=new LoadingScreen(gameScreen);
        this.getScene().setRoot(loadingScreen);
    }
}
