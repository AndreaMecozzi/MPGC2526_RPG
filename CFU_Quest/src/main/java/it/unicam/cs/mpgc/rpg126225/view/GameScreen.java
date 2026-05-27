package it.unicam.cs.mpgc.rpg126225.view;

import it.unicam.cs.mpgc.rpg126225.controller.GameScreenController;
import it.unicam.cs.mpgc.rpg126225.utils.RetroButton;
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

public class GameScreen extends BorderPane implements Screen, Musicabile {

    private static final String FONT_PATH = "/fonts/vt323.ttf";

    private final GameScreenController gameScreenController;
    private final Font vt323Top = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 30);
    
    private MediaPlayer musicaGioco;
    private Label cfuText, playerText, prossimoEsameText, testo;
    private VBox containerOpzioni;
    private Button menuButton;

    public GameScreen() {
        this.setStyle("-fx-background-color: black;");
        inizializzaMusica();
        inizializza();
        // Nota: Il controller viene inizializzato dopo la UI
        this.gameScreenController = new GameScreenController(this);
    }

    @Override
    public void inizializza() {
        inizializzaTop();
        inizializzaCentro();
        inizializzaBottom();
    }

    private void inizializzaTop() {
        // Uso dei metodi helper per creare i componenti in modo uniforme (DRY)
        HBox hbCfu = creaInfoBox("/images/coin_CFU.png", this.cfuText = creaLabel("CFU: "));
        HBox hbPlayer = creaInfoBox("/images/player.png", this.playerText = creaLabel("Nome: "));
        HBox hbLibroEsame = creaInfoBox("/images/libro.png", this.prossimoEsameText = creaLabel("Prossimo Esame: Metodologie di programmazione"));

        HBox hbCfuNome = new HBox(100, hbCfu, hbPlayer);
        hbCfuNome.setAlignment(Pos.CENTER_LEFT);

        VBox vbTop = new VBox(hbCfuNome, hbLibroEsame);
        vbTop.setPadding(new Insets(10, 0, 0, 10));
        this.setTop(vbTop);
    }

    private void inizializzaCentro() {
        this.testo = new Label("Questo è il testo dell'evento. Qui sotto puoi trovare una serie di scelte...");
        this.testo.setFont(vt323Top);
        this.testo.setStyle("-fx-text-fill: white; -fx-border-color: white;");
        this.testo.setPadding(new Insets(10));
        this.testo.setWrapText(true);

        HBox hbTesto = new HBox(this.testo);
        hbTesto.setMaxWidth(700);
        hbTesto.setPadding(new Insets(20, 0, 0, 0));
        this.setCenter(hbTesto);
    }

    private void inizializzaBottom() {
        this.containerOpzioni = new VBox(15);
        this.containerOpzioni.setAlignment(Pos.CENTER);
        this.containerOpzioni.setPadding(new Insets(0, 0, 120, 0));

        this.menuButton = new RetroButton("Menu");

        this.menuButton.setMinWidth(100); 
        this.menuButton.setPrefSize(100, 40);
        this.menuButton.setMaxSize(100, 40);
        this.menuButton.setOnAction(e -> this.gameScreenController.tornaAlMenu());

        StackPane bottomStack = new StackPane(this.containerOpzioni, this.menuButton);
        StackPane.setAlignment(this.menuButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(this.menuButton, new Insets(0, 0, 10, 10));
        this.setBottom(bottomStack);
    }


    private Label creaLabel(String testoDefault) {
        Label lbl = new Label(testoDefault);
        lbl.setFont(vt323Top);
        lbl.setStyle("-fx-text-fill: white;");
        return lbl;
    }

    private HBox creaInfoBox(String imagePath, Label label) {
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm()));
        iv.setPreserveRatio(true);
        iv.setFitHeight(50);
        
        HBox hb = new HBox(iv, label);
        hb.setAlignment(Pos.CENTER_LEFT);
        return hb;
    }

    public void inizializzaMusica() {
        String path = Objects.requireNonNull(getClass().getResource("/audio/Cobblestones_at_Midnight.mp3")).toExternalForm();
        musicaGioco = new MediaPlayer(new Media(path));
        musicaGioco.setCycleCount(MediaPlayer.INDEFINITE);
        musicaGioco.setVolume(0.05);
    }

    @Override
    public void avviaMusica() { musicaGioco.play(); }

    public MediaPlayer getMusicaGioco() { return musicaGioco; }
    public Label getCfuText() { return cfuText; }
    public Label getPlayerText() { return playerText; }
    public Label getProssimoEsameText() { return prossimoEsameText; }
    public Label getTesto() { return testo; }
    public VBox getContainerOpzioni() { return containerOpzioni; }
    public Button getMenuButton() { return menuButton; }
}
