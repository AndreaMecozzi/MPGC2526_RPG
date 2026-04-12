package it.unicam.cs.mpgc.rpg126225.model.giocatore;


/**
 * Definisce il contratto per la gestione dello stato del protagonista all'interno del gioco.
 *
 * @author Andrea Mecozzi
 */
public interface Player {

    /**
     * Restituisce il nome identificativo dello studente.
     *
     * @return una stringa contenente il nome del protagonista.
     */
    public String getNome();

    /**
     * Restituisce il numero totale di CFU guadagnati fino a questo momento.
     *
     * @return il conteggio attuale dei CFU.
     */
    public int cfuAccumulati();

    /**
     * Incrementa il punteggio del giocatore aggiungendo i CFU ottenuti
     * dal superamento di un esame o di un evento speciale.
     *
     * @param cfu il numero di crediti da sommare al totale attuale.
     */
    public void aggiungiCfu(int cfu);

    /**
     * Restituisce il nomedel prossimo esame che il giocatore deve sostenere
     * per proseguire nella storia.
     *
     * @return una stringa che rappresenta l'esame obiettivo corrente.
     */
    public String prossimoEsame();

    /**
     * Aggiorna l'obiettivo accademico del giocatore, impostando il riferimento
     * al nuovo esame da sbloccare.
     *
     * @param esame il nome o l'ID del nuovo esame obiettivo.
     */
    public void cambiaProssimoEsame(String esame);
}