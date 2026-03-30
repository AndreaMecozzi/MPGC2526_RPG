package it.unicam.cs.mpgc.rpg126225.persistence;

import java.io.IOException;

public interface Persistence {
    public void nuovaPartita();
    public void caricaPartita() throws IOException;
    public void salvaPartita() throws IOException;
}
