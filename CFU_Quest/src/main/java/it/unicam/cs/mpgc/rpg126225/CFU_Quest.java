package it.unicam.cs.mpgc.rpg126225;

import it.unicam.cs.mpgc.rpg126225.model.GameManager;
import it.unicam.cs.mpgc.rpg126225.persistence.XMLPersistence;
import it.unicam.cs.mpgc.rpg126225.view.MenuScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.Objects;

public class CFU_Quest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        GameManager.getInstance().setPersistenza(new XMLPersistence());
        MenuScreen menu=new MenuScreen();
        Scene scene=new Scene(menu);
        stage.setScene(scene);
        stage.setTitle("CFU Quest");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.toFront();
        stage.getIcons()
                .add(new Image(Objects.requireNonNull(getClass()
                        .getResourceAsStream("/images/logo.png"))));

        menu.avviaMusica();
        stage.show();
    }
}