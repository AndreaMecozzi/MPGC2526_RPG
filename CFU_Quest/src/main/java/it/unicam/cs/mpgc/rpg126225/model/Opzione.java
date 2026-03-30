package it.unicam.cs.mpgc.rpg126225.model;

import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg126225.model.eventi.EventoBase;
import jakarta.persistence.*;
/**
 * Una scelta influenza il percorso del giocatore. Essa è composta da una descrizione e
 * dall'evento che verrà scatenato se selezionata. (Poteva essere un record, ma per JPA
 * è stata trasformata in classe)
 */
@Entity
public class Opzione{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testo;
    @ManyToOne(targetEntity = EventoBase.class)
    @JoinColumn(name = "prossimo_evento_id")
    private Evento prossimoEvento;

    public Opzione(){}

    public Opzione(String testo, Evento prossimoEvento){
        this.testo = testo;
        this.prossimoEvento = prossimoEvento;
    }

    public String getTesto() {
        return this.testo;
    }

    public Evento getProssimoEvento() {
        return this.prossimoEvento;
    }
}
