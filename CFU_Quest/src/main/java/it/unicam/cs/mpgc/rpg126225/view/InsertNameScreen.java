package it.unicam.cs.mpgc.rpg126225.view;

import it.unicam.cs.mpgc.rpg126225.controller.InsertNameScreenController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InsertNameScreen extends BorderPane {
    private InsertNameScreenController insertNameController;
    private final Font vt323text=Font.loadFont(
            getClass().getResourceAsStream("/fonts/vt323.ttf"), 30);

    private Label textLabel;
    private TextField playerName;

    private Label errorLabel;

    private Button confirmButton;
    private Button backButton;


    public InsertNameScreen() {
        this.insertNameController=new InsertNameScreenController(this);
        this.setStyle("-fx-background-color: black;");
        inizializza();
    }

    public void inizializza() {
        /// Label e TextField
        this.textLabel = new Label("Prima di cominciare la tua avventura,\ndovresti dirmi come ti chiami:");
        this.textLabel.setStyle("-fx-text-fill: white;");
        this.textLabel.setFont(vt323text);
        this.textLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        this.playerName = new TextField();
        this.playerName.setFont(vt323text);
        this.playerName.setMaxWidth(400);
        this.playerName.setPromptText("Inserisci il nome...");
        this.playerName.setAlignment(Pos.CENTER);
        this.playerName.setStyle(
                "-fx-control-inner-background: black; " + "-fx-text-fill: white; " +
                        "-fx-border-color: white; " + "-fx-border-width: 2px; " +
                        "-fx-background-color: black;"+ "-fx-prompt-text-fill: grey;"
        );

        /// Label errore
        this.errorLabel = new Label("Nome non valido!");
        this.errorLabel.setFont(vt323text);
        this.errorLabel.setStyle("-fx-text-fill: red;"); // Rosso brillante
        this.errorLabel.setVisible(false);
        this.errorLabel.setManaged(false); // Non occupa spazio finché non è visibile

        VBox containerCentrale = new VBox(20);
        containerCentrale.setAlignment(Pos.CENTER);
        containerCentrale.getChildren().addAll(this.textLabel, this.playerName, this.errorLabel);

        this.setCenter(containerCentrale);

        /// Bottoni
        this.confirmButton = creaBottone("Conferma");
        this.confirmButton.setOnAction(e->this.insertNameController.controllaNome());


        this.backButton = creaBottone("Indietro");
        this.backButton.setOnAction(e->this.insertNameController.tornaAlMenu());

        HBox containerBottoni = new HBox(40); // Spazio di 40px tra i due bottoni
        containerBottoni.setAlignment(Pos.CENTER);
        containerBottoni.setPadding(new Insets(0, 0, 50, 0)); // Margine dal fondo dello schermo
        containerBottoni.getChildren().addAll(this.backButton, this.confirmButton);

        this.setBottom(containerBottoni);
    }


    public Button creaBottone(String testo){
        //Struttura standard
        Button button=new Button(testo);
        button.setFont(vt323text);
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

    public String getPlayerName(){
        return this.playerName.getText();
    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }
}
