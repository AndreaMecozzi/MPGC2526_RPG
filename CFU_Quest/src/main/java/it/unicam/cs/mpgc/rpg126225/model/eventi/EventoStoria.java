package it.unicam.cs.mpgc.rpg126225.model.eventi;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import jakarta.persistence.*;

import java.util.List;

/**
 * Un evento della storia è un evento che determina il futuro del giocatore.
 */
@Entity
@DiscriminatorValue("STORIA")
public class EventoStoria extends EventoBase {
    private  String descrizione;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id")
    private List<Opzione> scelte;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    public EventoStoria() {}

    public EventoStoria(String descrizione, List<Opzione> scelte){
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
    public Evento eseguiOpzione(Opzione scelta, Player p){
        return scelta.getProssimoEvento();
    }

    @Override
    public TipoEvento getTipo() {
        return this.tipoEvento;
    }

}
