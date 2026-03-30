package it.unicam.cs.mpgc.rpg126225.controller;

import it.unicam.cs.mpgc.rpg126225.view.GameScreen;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameScreenController {
    GameScreen gameScreen;

    public GameScreenController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
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
}
