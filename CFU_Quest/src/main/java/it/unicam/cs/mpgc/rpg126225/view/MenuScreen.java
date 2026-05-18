package it.unicam.cs.mpgc.rpg126225.view;

import it.unicam.cs.mpgc.rpg126225.controller.MenuScreenController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.util.Objects;

public class MenuScreen extends VBox implements Screen, Musicabile {

    private static final String STYLE_BASE = "-fx-background-color: black; -fx-border-color: white; -fx-text-fill: white;";
    private static final String STYLE_HOVER = "-fx-background-color: black; -fx-border-color: grey; -fx-text-fill: grey;";
    private static final String STYLE_PRESSED = "-fx-background-color: white; -fx-border-color: black; -fx-text-fill: black;";
    private static final String STYLE_DISABLED = "-fx-background-color: black; -fx-border-color: grey; -fx-text-fill: grey;";
    
    private final MenuScreenController menuScreenController;
    private final Font pressStart2pTitle = Font.loadFont(getClass().getResourceAsStream("/fonts/pressStart2p.ttf"), 60);
    private final Font vt323button = Font.loadFont(getClass().getResourceAsStream("/fonts/vt323.ttf"), 30);

    private MediaPlayer musicaMenu;
    private Button newGame, loadGame, exit;

    public MenuScreen() {
        this.setStyle("-fx-background-color: black;");
        this.setAlignment(Pos.TOP_CENTER);
        this.menuScreenController = new MenuScreenController(this);
        inizializzaMusica();
        inizializza();
    }

    @Override
    public void inizializza() {
        // Header (Logo + Titolo)
        HBox containerTitle = inizializzaHeader();
        VBox.setMargin(containerTitle, new Insets(25, 0, 0, 0)); // Parte esattamente a Y=25

        // Bottoni
        inizializzaBottoni();
        VBox containerButtons = new VBox(20, newGame, loadGame, exit);
        containerButtons.setAlignment(Pos.CENTER);
        
        // Ridotto il margine a 10 per far risalire i bottoni
        VBox.setMargin(containerButtons, new Insets(10, 0, 0, 0)); 

        this.getChildren().addAll(containerTitle, containerButtons);
    }

    private HBox inizializzaHeader() {
        ImageView logoUnicam = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png"))));
        logoUnicam.setPreserveRatio(true);
        logoUnicam.setFitWidth(150);

        Label titolo = new Label("CFU QUEST");
        titolo.setFont(pressStart2pTitle);
        titolo.setStyle("-fx-text-fill: white;");

        HBox header = new HBox(35, logoUnicam, titolo);
        header.setAlignment(Pos.CENTER);
        return header;
    }

    private void inizializzaBottoni() {
        this.newGame = creaBottone("Nuova partita");
        this.newGame.setOnAction(e -> menuScreenController.chiediNome());

        this.loadGame = creaBottone("Carica partita");
        if (!menuScreenController.esisteSalvataggio()) {
            disabilitaBottone(this.loadGame);
        }
        this.loadGame.setOnAction(e -> menuScreenController.caricaPartita());

        this.exit = creaBottone("Esci");
        this.exit.setOnAction(e -> System.exit(0));
    }

    public Button creaBottone(String testo) {
        Button btn = new Button(testo);
        btn.setFont(vt323button);
        btn.setStyle(STYLE_BASE);
        btn.setPrefSize(275, 60);

        btn.setOnMousePressed(e -> btn.setStyle(STYLE_PRESSED));
        btn.setOnMouseReleased(e -> btn.setStyle(STYLE_BASE));
        btn.setOnMouseEntered(e -> btn.setStyle(STYLE_HOVER));
        btn.setOnMouseExited(e -> btn.setStyle(STYLE_BASE));

        return btn;
    }

    private void disabilitaBottone(Button bottone) {
        bottone.setDisable(true);
        bottone.setStyle(STYLE_DISABLED);
    }

    public void inizializzaMusica() {
        String path = Objects.requireNonNull(getClass().getResource("/audio/Kingdom_Awaits.mp3")).toExternalForm();
        musicaMenu = new MediaPlayer(new Media(path));
        musicaMenu.setCycleCount(MediaPlayer.INDEFINITE);
        musicaMenu.setVolume(0.05);
    }

    @Override
    public void avviaMusica() { musicaMenu.play(); }

    // --- Getter ---
    public MediaPlayer getMusicaMenu() { return musicaMenu; }
    public Button getNewGame() { return newGame; }
    public Button getLoadGame() { return loadGame; }
    public Button getExit() { return exit; }
}
