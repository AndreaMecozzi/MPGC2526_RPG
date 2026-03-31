package it.unicam.cs.mpgc.rpg126225.controller;

import it.unicam.cs.mpgc.rpg126225.model.GameManager;
import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.persistence.XMLPersistence;
import it.unicam.cs.mpgc.rpg126225.view.GameScreen;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class GameScreenController {
    private GameScreen gameScreen;
    private GameManager gameManager;
    private final XMLPersistence persistence=new XMLPersistence();

    public GameScreenController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.gameManager=GameManager.getInstance();
        aggiornaDati();
    }


    public void tornaAlMenu(){
        this.gameScreen.setDisable(true);
        this.gameScreen.getMusicaGioco().stop();
        PauseTransition pausa=new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(e -> {
            MenuScreen menuScreen=new MenuScreen();
            menuScreen.avviaMusica();
            this.gameScreen.getScene().setRoot(menuScreen);
        });
        pausa.play();
    }

    public void aggiornaDati(){
        if(this.gameManager.getEventoAttuale()==null){
            this.gameScreen.getContainerOpzioni().setVisible(false);
            this.gameScreen.getContainerOpzioni().setDisable(true);
        }else{
            this.gameScreen.getCfuText().setText("CFU: "+
                    this.gameManager.getPlayer().cfuAccumulati());
            this.gameScreen.getProssimoEsameText().setText("Prossimo esame: "+
                    this.gameManager.getPlayer().prossimoEsame());
            this.gameScreen.getPlayerText().setText("Nome: "+
                    this.gameManager.getPlayer().getNome());
            this.gameScreen.getTesto().setText(gameManager.getEventoAttuale().getStoria());

            // Generazione dinamica dei bottoni
            this.gameScreen.getContainerOpzioni().getChildren().clear(); // Rimuove i bottoni del vecchio evento

            for (Opzione o : gameManager.getEventoAttuale().getOpzioni()) {
                // Creiamo un bottone per ogni opzione disponibile nell'evento
                Button btnScelta = gameScreen.creaBottone(o.testo());

                btnScelta.setOnAction(e -> {
                    gameManager.eseguiTurno(o);
                    // Salvataggio automatico per garantire la persistenza ad ogni passo [cite: 31]
                    try {
                        this.persistence.salvaPartita();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    aggiornaDati(); // Ricarica la schermata con il nuovo evento
                });

                // Aggiungiamo il bottone al contenitore della View
                this.gameScreen.getContainerOpzioni().getChildren().add(btnScelta);
            }
        }
    }
}
