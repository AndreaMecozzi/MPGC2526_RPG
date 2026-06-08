package it.unicam.cs.mpgc.rpg126225;

import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

/**
 * Rappresenta i dati caricati per trasferire lo stato di un salvataggio
 * di gioco dal livello di persistenza al core logico dell'applicazione.
 *
 * @param p L'istanza del Player con le statistiche e i progressi ripristinati.
 * @param idEvento L'identificativo univoco dell'ultimo evento attivo al momento del salvataggio.
 * @author Andrea Mecozzi
 */
public record GameState (Player p, String idEvento) {}
