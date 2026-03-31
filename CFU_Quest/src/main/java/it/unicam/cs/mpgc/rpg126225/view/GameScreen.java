package it.unicam.cs.mpgc.rpg126225.view;

import it.unicam.cs.mpgc.rpg126225.controller.GameScreenController;
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



public class GameScreen extends BorderPane implements Musicabile {

    private GameScreenController gameScreenController;

    private final Font vt323Top=Font.loadFont(getClass().
            getResourceAsStream("/fonts/vt323.ttf"), 30);
    private final Font vt323button=
            Font.loadFont(getClass().getResourceAsStream("/fonts/vt323.ttf"), 20);
    private MediaPlayer musicaGioco;

    /// Top
    private ImageView cfuImage;
    private Label cfuText;
    private ImageView playerImage;
    private Label playerText;
    private ImageView esameImage;
    private Label prossimoEsameText;
    /// Center
    private Label testo;
    /// Bottom
    private VBox containerOpzioni;
    private Button menuButton;

    public GameScreen() {
        this.setStyle("-fx-background-color: black;");
        inizializza();
        inizializzaMusica();
        this.gameScreenController=new GameScreenController(this);
    }

    public void inizializzaMusica(){
        String path=this.getClass().getResource("/audio/Cobblestones_at_Midnight.mp3")
                .toExternalForm();
        Media musica=new Media(path);
        musicaGioco=new MediaPlayer(musica);
        musicaGioco.setCycleCount(MediaPlayer.INDEFINITE);
        musicaGioco.setVolume(0.5);
    }

    @Override
    public void avviaMusica() {
        musicaGioco.play();
    }

    public void inizializza(){
        /// Parte superiore della schermata
        inizializzaTop();

        /// Parte centrale della schermata
        inizializzaCentro();

        ///  Parte inferiore della schermata
        inizializzaBottom();
    }

    public void inizializzaTop(){
        // Label CFU
        this.cfuImage=new ImageView();
        Image coin=new Image(this.getClass().getResource("/images/coin_CFU.png").toExternalForm());
        this.cfuImage.setImage(coin);
        this.cfuImage.setPreserveRatio(true);
        this.cfuImage.setFitHeight(50);
        this.cfuText=new Label("CFU: 100");
        this.cfuText.setFont(vt323Top);
        this.cfuText.setStyle("-fx-text-fill: white;");
        HBox hbCfu=new HBox();
        hbCfu.getChildren().addAll(this.cfuImage,this.cfuText);
        hbCfu.setAlignment(Pos.CENTER_LEFT);

        this.playerText=new Label("Nome: ");
        this.playerText.setFont(vt323Top);
        this.playerText.setStyle("-fx-text-fill: white;");
        HBox hbPlayer=new HBox();
        hbPlayer.getChildren().addAll(this.playerText);
        hbPlayer.setAlignment(Pos.CENTER_LEFT);

        HBox hbCfuNome=new HBox(100);
        hbCfuNome.setAlignment(Pos.CENTER_LEFT);
        hbCfuNome.getChildren().addAll(hbCfu, hbPlayer);

        // Label prossimo esame
        this.esameImage=new ImageView();
        Image libro=new Image(this.getClass().getResource("/images/libro.png").toExternalForm());
        this.esameImage.setImage(libro);
        this.esameImage.setPreserveRatio(true);
        this.esameImage.setFitHeight(50);
        this.prossimoEsameText=new Label("Prossimo Esame: Metodologie di programmazione");
        this.prossimoEsameText.setFont(vt323Top);
        this.prossimoEsameText.setStyle("-fx-text-fill: white;");
        HBox hbLibroEsame=new HBox();
        hbLibroEsame.getChildren().addAll(this.esameImage,this.prossimoEsameText);
        hbLibroEsame.setAlignment(Pos.CENTER_LEFT);


        VBox vbTop=new VBox();
        vbTop.getChildren().addAll(hbCfuNome, hbLibroEsame);
        vbTop.setPadding(new Insets(10,0,0,10));

        this.setTop(vbTop);
    }

    public void inizializzaCentro(){
        this.testo=new Label("Questo è il testo dell'evento. " +
                "Qui sotto puoi trovare una serie di scelte, ma ricorda: " +
                "qualsiasi cosa farai avrà un impatto sulla tua storia!");
        this.testo.setFont(vt323Top);
        this.testo.setStyle("-fx-text-fill: white;"+
                "-fx-border-color: white");
        this.testo.setPadding(new Insets(10,10,10,10));
        this.testo.setWrapText(true);
        HBox hbTesto=new HBox();
        hbTesto.getChildren().add(this.testo);
        hbTesto.setMaxWidth(700);
        hbTesto.setPadding(new Insets(20,0,0,0));

        this.setCenter(hbTesto);
    }

    public void inizializzaBottom(){
        this.containerOpzioni = new VBox(15);
        this.containerOpzioni.setAlignment(Pos.CENTER);
        this.containerOpzioni.setPadding(new Insets(0, 0, 50, 0));

        this.menuButton = creaBottone("Menu");
        this.menuButton.setPrefSize(100, 40);
        this.menuButton.setOnAction(e -> this.gameScreenController.tornaAlMenu());

        StackPane bottomPane = new StackPane();

        bottomPane.getChildren().addAll(this.containerOpzioni, this.menuButton);

        StackPane.setAlignment(this.containerOpzioni, Pos.CENTER);
        StackPane.setAlignment(this.menuButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(this.menuButton, new Insets(0, 0, 10, 10));

        this.setBottom(bottomPane);
    }

    public Button creaBottone(String testo){
        //Struttura standard
        Button button=new Button(testo);
        button.setFont(vt323button);
        button.setStyle("-fx-background-color: black;"+
                "-fx-border-color: white;"+
                "-fx-text-fill: white;");
        button.setPrefSize(200,50);

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
        button.setWrapText(true);

        return button;
    }


    public MediaPlayer getMusicaGioco() {
        return musicaGioco;
    }

    public Label getCfuText(){
        return this.cfuText;
    }

    public Label getPlayerText() {
        return this.playerText;
    }

    public Label getProssimoEsameText(){
        return this.prossimoEsameText;
    }

    public Label getTesto(){
        return this.testo;
    }

    public VBox getContainerOpzioni(){
        return this.containerOpzioni;
    }

    public Button getMenuButton() {
        return menuButton;
    }
}
