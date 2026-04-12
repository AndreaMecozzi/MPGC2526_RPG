package it.unicam.cs.mpgc.rpg126225;

import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CFU_Quest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        MenuScreen menu=new MenuScreen();
        Scene scene=new Scene(menu);
        stage.setScene(scene);
        stage.setTitle("CFU Quest");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.toFront();
        menu.avviaMusica();
        stage.show();
    }
}