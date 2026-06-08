package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.GameState;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

import java.io.IOException;
import java.util.List;

/**
 * Definisce il contratto per la gestione della persistenza dei dati e l'accesso
 * alle informazioni strutturali del gioco.
 *
 * @author Andrea Mecozzi
 */
public interface Persistence {

    /**
     * Carica in memoria lo stato del gioco da un salvataggio precedente.
     *
     * @return GameState i dati caricati dal salvataggio
     * @throws IOException Se si verifica un errore durante la lettura
     * dal supporto fisico (es. file non trovato o corrotto).
     */
    public GameState caricaPartita() throws IOException;

    /**
     * Salva lo stato attuale della sessione di gioco sul supporto di memorizzazione.
     *
     * @param player l'istanza del giocatore
     * @param eventoAttuale l'evento in cui si trova attualmente il giocatore
     * @throws IOException Se si verifica un errore durante la scrittura
     * sul supporto fisico (es. permessi negati o spazio esaurito).
     */
    public void salvaPartita(Player player, Evento eventoAttuale) throws IOException;

    /**
     * Recupera la struttura completa di un evento narrativo o accademico
     * a partire dal suo identificativo univoco.
     * @param idEvento La stringa che identifica univocamente l'evento desiderato.
     * @return L'oggetto Evento corrispondente all'ID richiesto, null altrimenti
     */
    public Evento getEvento(String idEvento);

    /**
     * Recupera una lista di opzioni (scelte) a partire dai loro identificativi.
     * @param idOpzioni Una lista di stringhe contenente gli identificativi delle opzioni da caricare.
     * @return Una lista di oggetti Opzione corrispondenti agli ID forniti.
     */
    public List<Opzione> getOpzioni(List<String> idOpzioni);

    /**
     * Verifica la presenza di dati di salvataggio persistenti.
     *
     * @return true se è presente un salvataggio valido,
     *         false altrimenti.
     */
    public boolean verificaEsistenzaSalvataggio();
}