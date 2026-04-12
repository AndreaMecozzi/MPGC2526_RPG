package it.unicam.cs.mpgc.rpg126225.model.giocatore;

/**
 * Rappresenta l'implementazione concreta del protagonista (lo studente) all'interno del gioco.
 *
 * @author Andrea Mecozzi
 */
public class Studente implements Player {
    private String nome;
    private int cfuAccumulati;
    private String prossimoEsame;

    /**
     * Costruttore base per un nuovo studente all'inizio della carriera.
     *
     * @param nome Il nome scelto dal giocatore per il protagonista.
     */
    public Studente (String nome) {
        this.nome = nome;
    }

    /**
     * Costruttore utilizzato per inizializzare lo studente con un obiettivo specifico.
     *
     * @param nome          Il nome del protagonista.
     * @param prossimoEsame L'identificativo del primo esame da sostenere.
     */
    public Studente(String nome, String prossimoEsame) {
        this.nome = nome;
        this.prossimoEsame = prossimoEsame;
    }

    /**
     * Costruttore completo, ideale per la fase di caricamento dei dati (persistenza).
     * Permette di ripristinare uno stato di gioco precedentemente salvato.
     *
     * @param nome           Il nome del protagonista.
     * @param cfuAccumulati  Il numero di CFU già ottenuti.
     * @param prossimoEsame  L'ID dell'esame obiettivo corrente.
     */
    public Studente(String nome, int cfuAccumulati, String prossimoEsame) {
        this.nome = nome;
        this.cfuAccumulati = cfuAccumulati;
        this.prossimoEsame = prossimoEsame;
    }

    /**
     * Restituisce il nome del giocatore
     *
     * @return Il nome del giocatore.
     */
    @Override
    public String getNome() {
        return this.nome;
    }

    /**
     * Restituisce il totale dei CFU accumulati dallo studente
     *
     * @return Il totale dei CFU maturati.
     */
    @Override
    public int cfuAccumulati() {
        return this.cfuAccumulati;
    }

    /**
     * Incrementa il contatore interno dei CFU dello studente.
     *
     * @param cfu La quantità di crediti da aggiungere.
     */
    @Override
    public void aggiungiCfu(int cfu) {
        this.cfuAccumulati += cfu;
    }

    /**
     * Restituisce il prossimo esame
     *
     * @return L'identificativo dell'esame attualmente puntato dal giocatore.
     */
    @Override
    public String prossimoEsame() {
        return this.prossimoEsame;
    }

    /**
     * Aggiorna il puntatore all'esame successivo nella progressione narrativa.
     *
     * @param prossimoEsame Il nuovo ID esame da impostare.
     */
    @Override
    public void cambiaProssimoEsame(String prossimoEsame){
        this.prossimoEsame = prossimoEsame;
    }
}