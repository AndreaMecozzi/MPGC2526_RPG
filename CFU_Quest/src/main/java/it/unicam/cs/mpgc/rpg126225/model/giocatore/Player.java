package it.unicam.cs.mpgc.rpg126225.model.giocatore;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;

/**
 * Rappresenta il protagonista delle storia
 */
public interface Player {
    public String getNome();
    public int cfuAccumulati();
    public void aggiungiCfu(int cfu);
    public String prossimoEsame();
    public void cambiaProssimoEsame(String esame);
}
