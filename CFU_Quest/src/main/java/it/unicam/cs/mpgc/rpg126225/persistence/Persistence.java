package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;

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
     * Crea un nuovo profilo o sovrascrive i dati temporanei precedenti,
     * impostando il nome del nuovo protagonista.
     * @param playerName Il nome inserito dal giocatore all'inizio dell'avventura.
     */
    public void nuovaPartita(String playerName);

    /**
     * Carica in memoria lo stato del gioco da un salvataggio precedente.
     * @throws IOException Se si verifica un errore durante la lettura
     * dal supporto fisico (es. file non trovato o corrotto).
     */
    public void caricaPartita() throws IOException;

    /**
     * Salva lo stato attuale della sessione di gioco sul supporto di memorizzazione.
     * @throws IOException Se si verifica un errore durante la scrittura
     * sul supporto fisico (es. permessi negati o spazio esaurito).
     */
    public void salvaPartita() throws IOException;

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
}