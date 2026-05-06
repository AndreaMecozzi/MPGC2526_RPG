package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import java.util.List;

/**
 * Rappresenta un evento puramente narrativo all'interno della storia del gioco.
 *
 * @author Andrea Mecozzi
 */
public class EventoStoria implements Evento {
    private String id;
    private String descrizione;
    private List<Opzione> scelte;
    private TipoEvento tipoEvento;

    /**
     * Costruisce un nuovo evento narrativo.
     *
     * @param id          Identificativo univoco dell'evento, utilizzato per la persistenza.
     * @param descrizione Il testo narrativo che descrive la situazione corrente al giocatore.
     * @param scelte      La lista delle possibili opzioni di scelta associate a questo evento.
     */
    public EventoStoria(String id, String descrizione, List<Opzione> scelte) {
        this.id = id;
        this.descrizione = descrizione;
        this.scelte = scelte;
        this.tipoEvento = TipoEvento.STORIA;
    }

    /**
     * Restituisce il frammento di storia o la descrizione dell'evento corrente.
     *
     * @return Il testo descrittivo dell'evento.
     */
    @Override
    public String getStoria() {
        return this.descrizione;
    }

    /**
     * Restituisce la lista delle opzioni di scelta disponibili per far avanzare la narrazione.
     * @return Una lista di opzioni disponibili.
     */
    @Override
    public List<Opzione> getOpzioni() {
        return this.scelte;
    }

    /**
     * Esegue la scelta selezionata dal giocatore.
     *
     * @param scelta L'opzione selezionata dall'utente.
     * @param p      L'istanza del giocatore coinvolto nell'evento.
     * @return L'identificativo del prossimo evento verso cui la narrazione deve dirigersi.
     */
    @Override
    public String eseguiOpzione(Opzione scelta, Player p) {
        return scelta.idProssimoEvento();
    }

    /**
     * Restituisce l'identificativo univoco dell'evento storia.
     *
     * @return L'ID dell'evento.
     */
    @Override
    public String getId() {
        return this.id;
    }
}