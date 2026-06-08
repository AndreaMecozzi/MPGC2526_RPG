package it.unicam.cs.mpgc.rpg126225.model;

import it.unicam.cs.mpgc.rpg126225.GameState;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Studente;
import it.unicam.cs.mpgc.rpg126225.persistence.Persistence;
import it.unicam.cs.mpgc.rpg126225.persistence.XMLPersistence;

import java.io.IOException;

/**
 * Gestore centralizzato della logica di gioco, implementato tramite il pattern Singleton.
 *
 * @author Andrea Mecozzi
 */
public class GameManager {
    private static GameManager instance;
    private Player player;
    private Evento eventoAttuale;
    private Persistence persistenza;

    /**
     * Restituisce l'istanza univoca del gestore del gioco.
     * Garantisce che esista un solo punto di controllo per la sessione corrente.
     *
     * @return L'istanza Singleton di GameManager.
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
     * Avvia una nuova partita impostando lo stato iniziale ed effettuando il primo salvataggio.
     *
     * @param nome il nome del giocatore che sta iniziando la partita
     */
    public void nuovaPartita(String nome){
        Player p=new Studente(nome, 0, "Programmazione");
        this.player=p;
        this.eventoAttuale=this.persistenza.getEvento("EVT_INIZIO_01");

        try{
            this.salvaPartita();
        }catch (IOException e){
            System.out.println("Errore durante il salvataggio della partita");
        }
    }

    /**
     * Carica lo stato della partita salvata delegando al servizio di persistenza
     * e ripristina la sessione corrente in questo GameManager.
     *
     * @throws IOException Se si verificano errori nella lettura del file di salvataggio.
     */
    public void caricaPartita() throws IOException{
        GameState dati=this.persistenza.caricaPartita();
        this.player=dati.p();
        this.eventoAttuale=this.persistenza.getEvento(dati.idEvento());
    }

    /**
     * Salva lo stato corrente della partita (giocatore ed evento attuale)
     * delegando al servizio di persistenza.
     *
     * @throws IOException Se si verificano errori nella scrittura del file di salvataggio.
     */
    public void salvaPartita() throws IOException{
        if(this.player!=null && this.eventoAttuale!=null){
            this.persistenza.salvaPartita(this.player,this.eventoAttuale);
        }
    }

    /**
     * Verifica l'esistenza del file di salvataggio tramite la persistenza
     *
     * @return true se esiste, false altrimenti
     */
    public boolean verificaEsistenzaSalvataggio(){
        return this.persistenza.verificaEsistenzaSalvataggio();
    }

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
     * Configura il sistema di persistenza da utilizzare durante la durata del gioco
     *
     * @param persistenza il sistema di persistenza da utilizzare
     */
    public void setPersistenza(Persistence persistenza) {
        this.persistenza = persistenza;
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
        Evento prossimo = this.persistenza.getEvento(idProssimoEvento);

        if (prossimo == null) {
            System.err.println("Attenzione: L'opzione selezionata non porta a nessun evento (dead end)!");
        }

        this.eventoAttuale = prossimo;
    }
}