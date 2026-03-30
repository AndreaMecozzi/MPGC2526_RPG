package it.unicam.cs.mpgc.rpg126225.controller;

import it.unicam.cs.mpgc.rpg126225.model.GameManager;
import it.unicam.cs.mpgc.rpg126225.view.GameScreen;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameScreenController {
    private GameScreen gameScreen;
    private GameManager gameManager;

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
        this.gameScreen.getCfuText().setText("CFU: "+
                this.gameManager.getPlayer().cfuAccumulati());
        this.gameScreen.getProssimoEsameLabel().setText("Prossimo esame: "+
                this.gameManager.getPlayer().prossimoEsame());
        this.gameScreen.getTesto().setText(gameManager.getEventoAttuale().getStoria());
    }
}
