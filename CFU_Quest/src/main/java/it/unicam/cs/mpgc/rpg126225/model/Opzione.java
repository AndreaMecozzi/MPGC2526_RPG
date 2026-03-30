package it.unicam.cs.mpgc.rpg126225.model;

import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;

/**
 * Una scelta influenza il percorso del giocatore. Essa è composta da una descrizione e
 * dall'evento che verrà scatenato se selezionata
 */
public record Opzione(String testo, Evento prossimoEvento){
}
