package it.unicam.cs.mpgc.rpg126225.model.giocatore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Rappresenta lo studente protagonista della storia.
 */
@Entity
public class Studente implements Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int cfuAccumulati;
    private String prossimoEsame;

    public Studente(){}

    public Studente(String nome, String prossimoEsame) {
        this.nome = nome;
        this.prossimoEsame = prossimoEsame;
    }

    public Studente(String nome, int cfuAccumulati, String prossimoEsame) {
        this.nome = nome;
        this.cfuAccumulati = cfuAccumulati;
        this.prossimoEsame = prossimoEsame;
    }

    public Long getId() {
        return this.id;
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
