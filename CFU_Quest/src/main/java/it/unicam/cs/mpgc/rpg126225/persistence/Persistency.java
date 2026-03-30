package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

public interface Persistency {
    public void nuovaPartita();
    public Player caricaPartita();
    public void salva(Player p);
}
