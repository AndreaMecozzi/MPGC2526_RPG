package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.GameManager;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Studente;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class XMLPersistence implements Persistence {

    private final String FILE_SALVATAGGIO = "salvataggio.xml";

    @Override
    public void nuovaPartita() {
        //TODO implementare
    }

    @Override
    public void caricaPartita() throws IOException {
        File inputFile = new File(FILE_SALVATAGGIO);
        if (!inputFile.exists()) {
            throw new IOException("Nessun salvataggio trovato! Inizia una nuova partita.");
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Estrazione dati dal file XML
            String nome = doc.getElementsByTagName("nome").item(0).getTextContent();
            int cfu = Integer.parseInt(doc.getElementsByTagName("cfu").item(0).getTextContent());
            String prossimoEsame = doc.getElementsByTagName("prossimoEsame").item(0).getTextContent();
            // Per ora ignoriamo idEventoAttuale come richiesto, ma lo caricheremo in futuro

            // Creazione dello studente e aggiornamento del GameManager
            Studente caricato = new Studente(nome, cfu, prossimoEsame);
            GameManager.getInstance().setPlayer(caricato);

        } catch (Exception e) {
            throw new IOException("Errore nel caricamento del file salvataggio.xml: " + e.getMessage());
        }
    }

    @Override
    public void salvaPartita() throws IOException {
        //TODO implementare
    }

}