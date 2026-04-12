package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

import java.util.List;

/**
 * Definisce il contratto per un generico evento all'interno del gioco di ruolo.
 *
 * @author Andrea Mecozzi
 */
public interface Evento {

    /**
     * Restituisce la descrizione testuale dell'evento o il frammento di storia corrente.
     *
     * @return una stringa contenente il testo narrativo dell'evento.
     */
    public String getStoria();

    /**
     * Restituisce la lista delle opzioni (scelte) disponibili per l'utente in questo evento.
     *
     * @return una lista di oggetti {@link Opzione}.
     */
    public List<Opzione> getOpzioni();

    /**
     * Gestisce l'interazione del giocatore con una specifica opzione dell'evento.
     * Questa operazione è responsabile di aggiornare lo stato del giocatore (es. CFU)
     * e restituire il feedback testuale conseguente alla scelta effettuata.
     *
     * @param opzione la scelta selezionata dal giocatore.
     * @param player  l'istanza del giocatore che effettua la scelta.
     * @return una stringa che descrive l'esito della scelta o il passaggio narrativo successivo.
     */
    public String eseguiOpzione(Opzione opzione, Player player);

    /**
     * Restituisce l'identificativo univoco dell'evento.
     * Utile per la gestione della persistenza e per il tracciamento della progressione.
     *
     * @return l'ID univoco dell'evento.
     */
    public String getId();
}