package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.Opzione;
import it.unicam.cs.mpgc.rpg126225.model.eventi.Evento;

import java.io.IOException;
import java.util.List;

public interface Persistence {
    public void nuovaPartita();
    public void caricaPartita() throws IOException;
    public void salvaPartita() throws IOException;
    public Evento getEvento(String idEvento);
    public List<Opzione> getOpzioni(List<String> idOpzioni);
}
