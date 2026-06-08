package it.unicam.cs.mpgc.rpg126225.controller;

import it.unicam.cs.mpgc.rpg126225.model.GameManager;
import it.unicam.cs.mpgc.rpg126225.view.GameScreen;
import it.unicam.cs.mpgc.rpg126225.view.InsertNameScreen;
import it.unicam.cs.mpgc.rpg126225.view.LoadingScreen;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;

public class MenuScreenController {
    private MenuScreen menuScreen;
    private GameManager gm;

    public MenuScreenController(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
        this.gm=GameManager.getInstance();
    }

    public void vaiAlGioco(){
        menuScreen.getMusicaMenu().stop();
        GameScreen gameScreen=new GameScreen();
        LoadingScreen loadingScreen=new LoadingScreen(gameScreen);
        menuScreen.getScene().setRoot(loadingScreen);
    }


    public void caricaPartita(){
        try{
            gm.caricaPartita();
            vaiAlGioco();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void chiediNome(){
        menuScreen.getMusicaMenu().stop();
        InsertNameScreen insertNameScreen=new InsertNameScreen();
        menuScreen.getScene().setRoot(insertNameScreen);
    }

    public boolean esisteSalvataggio() {
        return this.gm.verificaEsistenzaSalvataggio();
    }
}
