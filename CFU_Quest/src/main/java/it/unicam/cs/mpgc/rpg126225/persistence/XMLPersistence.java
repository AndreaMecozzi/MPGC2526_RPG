package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.GameManager;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Studente;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLPersistence implements Persistence {

    private final String FILE_SALVATAGGIO = "salvataggio.xml";

    @Override
    public void nuovaPartita() {
        // 1. Reset del Modello con dati iniziali
        Studente defaultPlayer = new Studente("Test", 0,
                "Programmazione");
        GameManager.getInstance().setPlayer(defaultPlayer);

        // 2. Sovrascrittura del file fisico per resettare il progresso
        try {
            salvaPartita();
        } catch (IOException e) {
            System.err.println("Errore nel reset del salvataggio: " + e.getMessage());
        }
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
        Player p = GameManager.getInstance().getPlayer();
        if (p == null) return;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Radice: <salvataggio>
            Element rootElement = doc.createElement("salvataggio");
            doc.appendChild(rootElement);

            // <nome>
            Element nome = doc.createElement("nome");
            nome.appendChild(doc.createTextNode(p.getNome()));
            rootElement.appendChild(nome);

            // <cfu>
            Element cfu = doc.createElement("cfu");
            cfu.appendChild(doc.createTextNode(String.valueOf(p.cfuAccumulati())));
            rootElement.appendChild(cfu);

            // <prossimoEsame>
            Element prossimoEsame = doc.createElement("prossimoEsame");
            prossimoEsame.appendChild(doc.createTextNode(p.prossimoEsame()));
            rootElement.appendChild(prossimoEsame);

            // Trasformazione dell'albero DOM in file XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Impostiamo l'indentazione per rendere il file leggibile
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_SALVATAGGIO));
            transformer.transform(source, result);

        } catch (Exception e) {
            throw new IOException("Errore durante la scrittura del file XML: " + e.getMessage());
        }
    }

}