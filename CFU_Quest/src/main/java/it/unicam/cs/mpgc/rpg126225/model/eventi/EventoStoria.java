package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;

import java.util.List;

/**
 * Un evento della storia è un evento che determina il futuro del giocatore.
 */
public class EventoStoria implements Evento {
    private String id;
    private  String descrizione;
    private List<Opzione> scelte;
    private TipoEvento tipoEvento;

    public EventoStoria(String id,String descrizione, List<Opzione> scelte){
        this.id = id;
        this.descrizione = descrizione;
        this.scelte = scelte;
        this.tipoEvento=TipoEvento.STORIA;
    }

    @Override
    public String getStoria() {
        return this.descrizione;
    }

    @Override
    public List<Opzione> getOpzioni() {
        return this.scelte;
    }

    @Override
    public String eseguiOpzione(Opzione scelta, Player p){
        return scelta.idProssimoEvento();
    }

    @Override
    public String getId() {
        return this.id;
    }

}
