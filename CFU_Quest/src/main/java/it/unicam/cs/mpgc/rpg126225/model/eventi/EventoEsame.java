package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

import java.util.List;

/**
 * Un esame è un evento che incide sull'avanzamento del giocatore. Esso è caratterizzato
 * da una domanda e da 4 risposte (di cui una esatta): se la risposta data è corretta,
 * allora è possibile assegnare i cfu attributi a tale esame al giocatore
 */
public class EventoEsame implements Evento {
    private String id;
    private String domanda;
    private List<Opzione> risposte;
    private Opzione rispostaEsatta;
    private int cfuAttributi;
    private String prossimoEsame;
    private TipoEvento tipoEvento;

    public EventoEsame(String id, String domanda,
                       List<Opzione> risposte, Opzione rispostaEsatta,
                       int cfuAttributi, String prossimoEsame) {
        this.id = id;
        this.domanda = domanda;
        this.risposte = risposte;
        this.rispostaEsatta = rispostaEsatta;
        this.cfuAttributi = cfuAttributi;
        this.prossimoEsame = prossimoEsame;
        this.tipoEvento=TipoEvento.ESAME;
    }


    @Override
    public String getStoria() {
        return this.domanda;
    }

    @Override
    public List<Opzione> getOpzioni() {
        return this.risposte;
    }

    @Override
    public Evento eseguiOpzione(Opzione rispostaScelta, Player p){
        if(this.rispostaEsatta.equals(rispostaScelta)){
            p.aggiungiCfu(this.cfuAttributi);
            p.cambiaProssimoEsame(this.prossimoEsame);
        }
        return rispostaScelta.prossimoEvento();
    }

    @Override
    public String getId() {
        return this.id;
    }
}
