package it.unicam.cs.mpgc.rpg126225.model;

import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import it.unicam.cs.mpgc.rpg126225.persistence.Persistence;
import it.unicam.cs.mpgc.rpg126225.persistence.XMLPersistence;

/**
 * Gestore centralizzato della logica di gioco, implementato tramite il pattern Singleton.
 *
 * @author Andrea Mecozzi
 */
public class GameManager {
    private static GameManager instance;
    private Player player;
    private Evento eventoAttuale;
    private final Persistence persistence = new XMLPersistence();

    /**
     * Restituisce l'istanza univoca del gestore del gioco.
     * Garantisce che esista un solo punto di controllo per la sessione corrente.
     *
     * @return L'istanza Singleton di {@code GameManager}.
     */
    public static GameManager getInstance(){
        if(instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Costruttore privato per impedire l'istanziazione esterna,
     * rispettando il pattern Singleton.
     */
    private GameManager(){}

    /**
     * Restituisce il giocatore attualmente attivo nella sessione
     *
     * @return L'istanza del giocatore attualmente attivo nella sessione.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Imposta il giocatore per la sessione corrente.
     *
     * @param player Il giocatore protagonista della storia.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Restituisce l'evento attualmente visualizzato dal giocatore
     *
     * @return L'evento attualmente visualizzato dal giocatore.
     */
    public Evento getEventoAttuale() {
        return eventoAttuale;
    }

    /**
     * Imposta manualmente l'evento corrente.
     *
     * @param eventoAttuale L'evento da sottoporre al giocatore.
     */
    public void setEventoAttuale(Evento eventoAttuale) {
        this.eventoAttuale = eventoAttuale;
    }

    /**
     * Elabora la scelta effettuata dal giocatore e aggiorna lo stato del gioco.
     *
     * @param opzione L'opzione selezionata dall'utente.
     */
    public void eseguiTurno(Opzione opzione){
        if (this.eventoAttuale == null) {
            System.err.println("Errore: Impossibile eseguire turno su un evento nullo!");
            return;
        }

        String idProssimoEvento = this.eventoAttuale.eseguiOpzione(opzione, player);
        Evento prossimo = this.persistence.getEvento(idProssimoEvento);

        if (prossimo == null) {
            System.err.println("Attenzione: L'opzione selezionata non porta a nessun evento (dead end)!");
        }

        this.eventoAttuale = prossimo;
    }
}