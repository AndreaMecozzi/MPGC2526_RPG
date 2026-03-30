package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

import java.util.List;

/**
 * Un evento della storia è un evento che determina il futuro del giocatore.
 */
public class EventoStoria implements Evento {
    private  String descrizione;
    private List<Opzione> scelte;

    public EventoStoria(String descrizione, List<Opzione> scelte){
        this.descrizione = descrizione;
        this.scelte = scelte;
    }

    @Override
    public String getStoria() {
        return this.descrizione;
    }

    @Override
    public List<Opzione> getOpzioni() {
        return this.scelte;
    }

    @Override
    public Evento eseguiOpzione(Opzione scelta, Player p){
        return scelta.prossimoEvento();
    }

}
