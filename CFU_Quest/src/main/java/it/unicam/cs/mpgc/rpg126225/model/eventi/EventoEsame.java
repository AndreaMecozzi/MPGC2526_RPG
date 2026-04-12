package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import java.util.List;

/**
 * Rappresenta una specifica tipologia di evento narrativo: l'esame universitario.
 *
 * @author Andrea Mecozzi
 */
public class EventoEsame implements Evento {
    private String id;
    private String domanda;
    private List<Opzione> risposte;
    private String rispostaEsatta;
    private int cfuAttributi;
    private String prossimoEsame;
    private TipoEvento tipoEvento;

    /**
     * Costruisce un nuovo evento di tipo esame.
     *
     * @param id             Identificativo univoco dell'esame.
     * @param domanda        Il testo del quesito da porre al giocatore.
     * @param risposte       La lista delle opzioni di risposta disponibili.
     * @param rispostaEsatta L'ID dell'opzione considerata corretta.
     * @param cfuAttributi   Il numero di crediti assegnati in caso di risposta esatta.
     * @param prossimoEsame  Il nome o ID dell'esame che verrà sbloccato in caso di successo.
     */
    public EventoEsame(String id, String domanda,
                       List<Opzione> risposte, String rispostaEsatta,
                       int cfuAttributi, String prossimoEsame) {
        this.id = id;
        this.domanda = domanda;
        this.risposte = risposte;
        this.rispostaEsatta = rispostaEsatta;
        this.cfuAttributi = cfuAttributi;
        this.prossimoEsame = prossimoEsame;
        this.tipoEvento = TipoEvento.ESAME;
    }

    /**
     * Restituisce il testo della domanda d'esame.
     *
     * @return La stringa contenente il quesito.
     */
    @Override
    public String getStoria() {
        return this.domanda;
    }

    /**
     * Restituisce l'elenco delle opzioni di risposta associate all'esame.
     *
     * @return Una lista di opzioni disponibili.
     */
    @Override
    public List<Opzione> getOpzioni() {
        return this.risposte;
    }

    /**
     * Esegue la logica di valutazione della risposta scelta.
     *
     * @param rispostaScelta L'opzione selezionata dall'utente nell'interfaccia.
     * @param p              Il giocatore che sta sostenendo l'esame.
     * @return L'identificativo del prossimo evento narrativo derivante dalla scelta.
     */
    @Override
    public String eseguiOpzione(Opzione rispostaScelta, Player p) {
        if(this.rispostaEsatta.equals(rispostaScelta.id())) {
            p.aggiungiCfu(this.cfuAttributi);
            p.cambiaProssimoEsame(this.prossimoEsame);
        }
        return rispostaScelta.idProssimoEvento();
    }

    /**
     * Restituisce l'identificativo univoco dell'esame.
     *
     * @return L'ID dell'esame.
     */
    @Override
    public String getId() {
        return this.id;
    }
}