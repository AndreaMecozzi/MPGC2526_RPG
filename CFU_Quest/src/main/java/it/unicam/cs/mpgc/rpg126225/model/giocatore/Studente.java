package it.unicam.cs.mpgc.rpg126225.model.giocatore;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;

/**
 * Rappresenta lo studente protagonista della storia.
 */
public class Studente implements Player {
    private String nome;
    private int cfuAccumulati;
    private String prossimoEsame;

    public Studente (String nome) {
        this.nome = nome;
    }

    public Studente(String nome, String prossimoEsame) {
        this.nome = nome;
        this.prossimoEsame = prossimoEsame;
    }

    public Studente(String nome, int cfuAccumulati, String prossimoEsame) {
        this.nome = nome;
        this.cfuAccumulati = cfuAccumulati;
        this.prossimoEsame = prossimoEsame;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public int cfuAccumulati() {
        return this.cfuAccumulati;
    }

    @Override
    public void aggiungiCfu(int cfu) {
        this.cfuAccumulati += cfu;
    }

    @Override
    public String prossimoEsame() {
        return this.prossimoEsame;
    }

    @Override
    public void cambiaProssimoEsame(String prossimoEsame){
        this.prossimoEsame = prossimoEsame;
    }
}
