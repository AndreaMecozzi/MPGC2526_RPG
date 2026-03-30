package it.unicam.cs.mpgc.rpg126225.controller;

import it.unicam.cs.mpgc.rpg126225.view.GameScreen;
import it.unicam.cs.mpgc.rpg126225.view.LoadingScreen;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;

public class MenuScreenController {
    public MenuScreen menuScreen;

    public MenuScreenController(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public void vaiAlGioco(){
        menuScreen.getMusicaMenu().stop();
        GameScreen gameScreen=new GameScreen();
        LoadingScreen loadingScreen=new LoadingScreen(gameScreen);
        menuScreen.getScene().setRoot(loadingScreen);
    }
}
