package it.unicam.cs.mpgc.rpg126225.view;

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;


public class GameScreen extends BorderPane implements Musicabile {
    private final Font vt323Top=Font.loadFont(getClass().
            getResourceAsStream("/fonts/vt323.ttf"), 30);
    private final Font vt323button=
            Font.loadFont(getClass().getResourceAsStream("/fonts/vt323.ttf"), 20);
    private MediaPlayer musicaGioco;

    public GameScreen() {
        this.setStyle("-fx-background-color: black;");
        inizializzaMusica();
        inizializza();
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
        ImageView coinCfu=new ImageView();
        Image coin=new Image(this.getClass().getResource("/images/coin_CFU.png").toExternalForm());
        coinCfu.setImage(coin);
        coinCfu.setPreserveRatio(true);
        coinCfu.setFitHeight(50);
        Label cfuText=new Label("CFU: 100");
        cfuText.setFont(vt323Top);
        cfuText.setStyle("-fx-text-fill: white;");
        HBox hbCoinCfu=new HBox();
        hbCoinCfu.getChildren().addAll(coinCfu,cfuText);
        hbCoinCfu.setAlignment(Pos.CENTER_LEFT);

        // Label prossimo esame
        ImageView libroEsame=new ImageView();
        Image libro=new Image(this.getClass().getResource("/images/libro.png").toExternalForm());
        libroEsame.setImage(libro);
        libroEsame.setPreserveRatio(true);
        libroEsame.setFitHeight(50);
        Label prossimoEsameLabel=new Label("Prossimo Esame: Metodologie di programmazione");
        prossimoEsameLabel.setFont(vt323Top);
        prossimoEsameLabel.setStyle("-fx-text-fill: white;");
        HBox hbLibroEsame=new HBox();
        hbLibroEsame.getChildren().addAll(libroEsame,prossimoEsameLabel);
        hbLibroEsame.setAlignment(Pos.CENTER_LEFT);


        VBox vbTop=new VBox();
        vbTop.getChildren().addAll(hbCoinCfu, hbLibroEsame);
        vbTop.setPadding(new Insets(10,0,0,10));

        this.setTop(vbTop);
    }

    public void inizializzaCentro(){
        Label testo=new Label("Questo è il testo dell'evento. " +
                "Qui sotto puoi trovare una serie di scelte, ma ricorda: " +
                "qualsiasi cosa farai avrà un impatto sulla tua storia!");
        testo.setFont(vt323Top);
        testo.setStyle("-fx-text-fill: white;"+
                "-fx-border-color: white");
        testo.setPadding(new Insets(10,10,10,10));
        testo.setWrapText(true);
        HBox hbTesto=new HBox();
        hbTesto.getChildren().add(testo);
        hbTesto.setMaxWidth(700);
        hbTesto.setPadding(new Insets(20,0,0,0));

        this.setCenter(hbTesto);
    }

    public void inizializzaBottom(){
        Button scelta1=creaBottone("Scelta 1");
        Button scelta2=creaBottone("Scelta 2");
        Button scelta3=creaBottone("Scelta 3");
        Button scelta4=creaBottone("Scelta 4");

        GridPane gridButtons=new GridPane();
        gridButtons.setHgap(10);
        gridButtons.setVgap(10);
        gridButtons.setAlignment(Pos.CENTER);
        gridButtons.add(scelta1,0,0);
        gridButtons.add(scelta2,0,1);
        gridButtons.add(scelta3,1,0);
        gridButtons.add(scelta4,1,1);

        gridButtons.setPadding(new Insets(0,0,100,0));

        Button menuButton=creaBottone("Menu");
        menuButton.setPrefSize(100,40);
        menuButton.setOnAction(e->tornaAlMenu());

        StackPane bottomPane=new StackPane();
        bottomPane.getChildren().addAll(gridButtons,menuButton);
        bottomPane.setAlignment(gridButtons,Pos.CENTER);
        bottomPane.setAlignment(menuButton,Pos.BOTTOM_LEFT);
        bottomPane.setMargin(menuButton,new Insets(0,0,10,10));

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

    public void tornaAlMenu(){
        this.setDisable(true);
        musicaGioco.stop();
        PauseTransition pausa=new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(e -> {
            MenuScreen menuScreen=new MenuScreen();
            menuScreen.avviaMusica();
            this.getScene().setRoot(menuScreen);
        });
        pausa.play();
    }
}
