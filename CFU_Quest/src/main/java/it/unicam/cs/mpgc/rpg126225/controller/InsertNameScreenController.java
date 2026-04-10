package it.unicam.cs.mpgc.rpg126225.controller;

import it.unicam.cs.mpgc.rpg126225.persistence.Persistence;
import it.unicam.cs.mpgc.rpg126225.persistence.XMLPersistence;
import it.unicam.cs.mpgc.rpg126225.view.GameScreen;
import it.unicam.cs.mpgc.rpg126225.view.InsertNameScreen;
import it.unicam.cs.mpgc.rpg126225.view.LoadingScreen;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;

public class InsertNameScreenController {
    private InsertNameScreen insertNameScreen;
    private Persistence persistence;

    public InsertNameScreenController(InsertNameScreen insertNameScreen) {
        this.insertNameScreen = insertNameScreen;
        this.persistence=new XMLPersistence();
    }

    public void tornaAlMenu(){
        MenuScreen menuScreen=new MenuScreen();
        menuScreen.avviaMusica();
        this.insertNameScreen.getScene().setRoot(menuScreen);
    }

    public void nuovaPartita(String playerName){
        this.persistence.nuovaPartita(playerName);
        vaiAlGioco();
    }

    public void vaiAlGioco(){
        GameScreen gameScreen=new GameScreen();
        LoadingScreen loadingScreen=new LoadingScreen(gameScreen);
        insertNameScreen.getScene().setRoot(loadingScreen);
    }

    public void controllaNome(){
        if(this.insertNameScreen.getPlayerName() == null||
                this.insertNameScreen.getPlayerName().length()<4||
                !this.insertNameScreen.getPlayerName().matches("^[a-zA-Z0-9 ]*$")){
                this.insertNameScreen.getErrorLabel().setVisible(true);
                this.insertNameScreen.getErrorLabel().setManaged(true);
        }else{
            this.nuovaPartita(this.insertNameScreen.getPlayerName());
        }
    }
}
