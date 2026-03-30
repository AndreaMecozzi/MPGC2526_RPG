package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

import java.util.List;

/**
 * Rappresenta un generico evento della storia. Un evento è composto da una descrizione
 * e da relative scelte, che influenzeranno il corso della storia del giocatore
 */
public interface Evento {
    public String getStoria();
    public List<Opzione> getOpzioni();
    public Evento eseguiOpzione(Opzione opzione, Player player);
    public TipoEvento getTipo();
}
