package it.unicam.cs.mpgc.rpg126225.model.eventi;

import jakarta.persistence.*;

@Entity
@Table(name = "eventi")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_evento")
public abstract class  EventoBase implements Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public EventoBase() {}

    public Long getId() {
        return this.id;
    }
}
