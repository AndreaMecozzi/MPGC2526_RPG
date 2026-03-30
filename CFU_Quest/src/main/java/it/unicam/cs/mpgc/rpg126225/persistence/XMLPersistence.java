package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.*;
import it.unicam.cs.mpgc.rpg126225.model.eventi.*;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Studente;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class XMLPersistence implements Persistence {

    private final String FILE_SALVATAGGIO = "salvataggio.xml";
    private final String PATH_STATIC = "src/main/resources/persistence/"; // Percorso per i file statici

    // Mappe per la ricostruzione del grafo
    private final Map<String, Evento> mappaEventi = new HashMap<>();
    private final Map<String, Opzione> mappaOpzioni = new HashMap<>();

    // Mappe temporanee per dati XML "grezzi"
    private final Map<String, List<String>> rawEventiOpzioni = new HashMap<>();
    private final Map<String, String> rawEventiCorretta = new HashMap<>();
    private final Map<String, String> rawOpzioniProssimo = new HashMap<>();
    private final Map<String, String> testiOpzioni = new HashMap<>();

    @Override
    public void nuovaPartita() {
        try {
            caricaStrutturaGioco();
            Studente defaultPlayer = new Studente("Studente", 0, "Analisi 1");
            GameManager.getInstance().setPlayer(defaultPlayer);
            // Partiamo dal primo evento definito
            GameManager.getInstance().setEventoAttuale(mappaEventi.get("EVT_INIZIO_01"));
            salvaPartita();
        } catch (Exception e) {
            System.err.println("Errore inizializzazione gioco: " + e.getMessage());
        }
    }

    @Override
    public void caricaPartita() throws IOException {
        try {
            caricaStrutturaGioco();

            File inputFile = new File(FILE_SALVATAGGIO);
            if (!inputFile.exists()) throw new IOException("Salvataggio non trovato");

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputFile);
            String nome = doc.getElementsByTagName("nome").item(0).getTextContent();
            int cfu = Integer.parseInt(doc.getElementsByTagName("cfu").item(0).getTextContent());
            String prossimoEsame = doc.getElementsByTagName("prossimoEsame").item(0).getTextContent();
            String idAttuale = doc.getElementsByTagName("idEventoAttuale").item(0).getTextContent();

            GameManager.getInstance().setPlayer(new Studente(nome, cfu, prossimoEsame));
            GameManager.getInstance().setEventoAttuale(mappaEventi.get(idAttuale));

        } catch (Exception e) {
            throw new IOException("Errore caricamento: " + e.getMessage());
        }
    }

    private void caricaStrutturaGioco() throws Exception {
        mappaEventi.clear(); mappaOpzioni.clear();
        parseOpzioni();
        parseEventi();
        costruisciGrafo();
    }

    private void parseOpzioni() throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PATH_STATIC + "opzioni.xml"));
        NodeList nodi = doc.getElementsByTagName("opzione");
        for (int i = 0; i < nodi.getLength(); i++) {
            Element e = (Element) nodi.item(i);
            String id = e.getAttribute("id");
            testiOpzioni.put(id, e.getElementsByTagName("testo").item(0).getTextContent());
            rawOpzioniProssimo.put(id, e.getElementsByTagName("idProssimoEvento").item(0).getTextContent());
        }
    }

    private void parseEventi() throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PATH_STATIC + "eventi.xml"));
        NodeList nodi = doc.getElementsByTagName("evento");
        for (int i = 0; i < nodi.getLength(); i++) {
            Element e = (Element) nodi.item(i);
            String id = e.getAttribute("id");
            String tipo = e.getAttribute("tipo");
            String desc = e.getElementsByTagName("descrizione").item(0).getTextContent();

            List<String> opzIds = new ArrayList<>();
            NodeList nScelte = e.getElementsByTagName("scelta");
            for(int j=0; j<nScelte.getLength(); j++) opzIds.add(nScelte.item(j).getTextContent());
            rawEventiOpzioni.put(id, opzIds);

            if (tipo.equals("STORIA")) {
                mappaEventi.put(id, new EventoStoria(id, desc, new ArrayList<>()));
            } else {
                int cfu = Integer.parseInt(e.getElementsByTagName("cfu").item(0).getTextContent());
                String prossimo = e.getElementsByTagName("prossimoEsame").item(0).getTextContent();
                rawEventiCorretta.put(id, e.getElementsByTagName("idSceltaCorretta").item(0).getTextContent());
                mappaEventi.put(id, new EventoEsame(id, desc, new ArrayList<>(), null, cfu, prossimo));
            }
        }
    }

    private void costruisciGrafo() throws Exception {
        // Creiamo le Opzioni (ora che gli eventi "gusci" esistono)
        for (String id : testiOpzioni.keySet()) {
            mappaOpzioni.put(id, new Opzione(id, testiOpzioni.get(id), mappaEventi.get(rawOpzioniProssimo.get(id))));
        }
        // Colleghiamo tutto
        for (Evento ev : mappaEventi.values()) {
            List<Opzione> reali = new ArrayList<>();
            for (String idOpt : rawEventiOpzioni.get(ev.getId())) reali.add(mappaOpzioni.get(idOpt));
            ev.getOpzioni().addAll(reali); // Popoliamo la lista

            if (ev instanceof EventoEsame esame) {
                Field f = EventoEsame.class.getDeclaredField("rispostaEsatta");
                f.setAccessible(true);
                f.set(esame, mappaOpzioni.get(rawEventiCorretta.get(ev.getId())));
            }
        }
    }

    @Override
    public void salvaPartita() throws IOException {
        Player p = GameManager.getInstance().getPlayer();
        Evento attuale = GameManager.getInstance().getEventoAttuale();
        if (p == null || attuale == null) return;

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("salvataggio");
            doc.appendChild(root);

            addElement(doc, root, "nome", p.getNome());
            addElement(doc, root, "cfu", String.valueOf(p.cfuAccumulati()));
            addElement(doc, root, "prossimoEsame", p.prossimoEsame());
            addElement(doc, root, "idEventoAttuale", attuale.getId());

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_SALVATAGGIO)));
        } catch (Exception e) { throw new IOException(e); }
    }

    private void addElement(Document doc, Element root, String tag, String val) {
        Element e = doc.createElement(tag); e.setTextContent(val); root.appendChild(e);
    }
}