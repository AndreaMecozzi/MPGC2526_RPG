package it.unicam.cs.mpgc.rpg126225.model;

import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

/**
 * Rappresenta il gestore del gioco. È una classe singleton che si occupa di
 * costruire il percorso dello studente.
 */
public class GameManager {
    private static GameManager instance;
    private Player player;
    private Evento eventoAttuale;

    public static GameManager getInstance(){
        if(instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager(){

    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Evento getEventoAttuale() {
        return eventoAttuale;
    }
    public void setEventoAttuale(Evento eventoAttuale) {
        this.eventoAttuale = eventoAttuale;
    }

    public void eseguiTurno(Opzione opzione){
        if (this.eventoAttuale == null) {
            System.err.println("Errore: Impossibile eseguire turno su un evento nullo!");
            return;
        }
        Evento prossimo = this.eventoAttuale.eseguiOpzione(opzione, player);

        if (prossimo == null) {
            System.err.println("Attenzione: L'opzione selezionata non porta a nessun evento (dead end)!");
        }

        this.eventoAttuale = prossimo;
    }
}
