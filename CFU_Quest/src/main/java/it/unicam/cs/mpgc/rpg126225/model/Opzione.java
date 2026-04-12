package it.unicam.cs.mpgc.rpg126225.model;

/**
 * Rappresenta una scelta disponibile all'interno di un evento narrativo o accademico.
 *
 * @param id               Identificativo univoco dell'opzione.
 * @param testo            La descrizione testuale della scelta visualizzata dall'utente.
 * @param idProssimoEvento L'identificativo dell'evento che verrà caricato nel GameManager
 *                         qualora questa opzione venga selezionata.
 *
 * * @author Andrea Mecozzi
 */
public record Opzione(String id, String testo, String idProssimoEvento) {
}