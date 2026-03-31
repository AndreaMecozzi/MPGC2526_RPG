package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.*;
import it.unicam.cs.mpgc.rpg126225.model.eventi.*;
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
    private final String PATH_STATIC = "src/main/resources/persistence/";

    // Mappe STATICHE per condividere la storia tra salvataggio e caricamento
    private static final Map<String, Evento> mappaEventi = new HashMap<>();
    private static final Map<String, Opzione> mappaOpzioni = new HashMap<>();
    private static final Map<String, List<String>> legamiEventoOpzioni = new HashMap<>();
    private static final Map<String, String> legamiOpzioneEvento = new HashMap<>();
    private static final Map<String, String> testiOpzioni = new HashMap<>();
    private static final Map<String, String> idRispostaCorrettaPerEsame = new HashMap<>();

    @Override
    public void nuovaPartita() {
        try {
            caricaStrutturaMondo();
            Studente p = new Studente("Test", 0, "Analisi 1");
            GameManager.getInstance().setPlayer(p);
            // Imposta l'evento iniziale (il primo trovato o uno specifico)
            GameManager.getInstance().setEventoAttuale(mappaEventi.get("EVT_INIZIO_01"));
            salvaPartita();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void caricaStrutturaMondo() throws Exception {
        if (!mappaEventi.isEmpty()) return; // Evita di ricaricare se già in memoria
        parseOpzioni();
        parseEventi();
        collegaGrafo();
    }

    private void parseOpzioni() throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PATH_STATIC + "opzioni.xml"));
        NodeList nodi = doc.getElementsByTagName("opzione");
        for (int i = 0; i < nodi.getLength(); i++) {
            Element el = (Element) nodi.item(i);
            String id = el.getAttribute("id");
            testiOpzioni.put(id, el.getElementsByTagName("testo").item(0).getTextContent());
            legamiOpzioneEvento.put(id, el.getElementsByTagName("idProssimoEvento").item(0).getTextContent());
        }
    }

    private void parseEventi() throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(PATH_STATIC + "eventi.xml"));
        NodeList nodi = doc.getElementsByTagName("evento");
        for (int i = 0; i < nodi.getLength(); i++) {
            Element el = (Element) nodi.item(i);
            String id = el.getAttribute("id");
            String tipo = el.getAttribute("tipo");
            String desc = el.getElementsByTagName("descrizione").item(0).getTextContent();

            List<String> optIds = new ArrayList<>();
            NodeList nScelte = el.getElementsByTagName("scelta");
            for (int j = 0; j < nScelte.getLength(); j++) optIds.add(nScelte.item(j).getTextContent());
            legamiEventoOpzioni.put(id, optIds);

            if (tipo.equals("STORIA")) {
                mappaEventi.put(id, new EventoStoria(id, desc, new ArrayList<>()));
            } else {
                int cfu = Integer.parseInt(el.getElementsByTagName("cfu").item(0).getTextContent());
                String prossimo = el.getElementsByTagName("prossimoEsame").item(0).getTextContent();
                idRispostaCorrettaPerEsame.put(id, el.getElementsByTagName("idSceltaCorretta").item(0).getTextContent());
                mappaEventi.put(id, new EventoEsame(id, desc, new ArrayList<>(), null, cfu, prossimo));
            }
        }
    }

    private void collegaGrafo() throws Exception {
        // 1. Crea i Record Opzione (immutabili)
        for (String id : testiOpzioni.keySet()) {
            Evento prox = mappaEventi.get(legamiOpzioneEvento.get(id));
            mappaOpzioni.put(id, new Opzione(id, testiOpzioni.get(id), prox));
        }
        // 2. Riempi gli Eventi e imposta risposte esatte
        for (String idEv : mappaEventi.keySet()) {
            Evento ev = mappaEventi.get(idEv);
            for (String idOpt : legamiEventoOpzioni.get(idEv)) {
                Opzione op = mappaOpzioni.get(idOpt);
                ev.getOpzioni().add(op);
                if (ev instanceof EventoEsame esame && idOpt.equals(idRispostaCorrettaPerEsame.get(idEv))) {
                    Field f = EventoEsame.class.getDeclaredField("rispostaEsatta");
                    f.setAccessible(true);
                    f.set(esame, op);
                }
            }
        }
    }

    @Override
    public void salvaPartita() throws IOException {
        GameManager gm = GameManager.getInstance();
        if (gm.getPlayer() == null || gm.getEventoAttuale() == null) return;

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("salvataggio");
            doc.appendChild(root);

            addElement(doc, root, "nome", gm.getPlayer().getNome());
            addElement(doc, root, "cfu", String.valueOf(gm.getPlayer().cfuAccumulati()));
            addElement(doc, root, "prossimoEsame", gm.getPlayer().prossimoEsame());
            addElement(doc, root, "idEventoAttuale", gm.getEventoAttuale().getId());

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(doc), new StreamResult(new File(FILE_SALVATAGGIO)));
        } catch (Exception e) { throw new IOException(e); }
    }

    @Override
    public void caricaPartita() throws IOException {
        try {
            caricaStrutturaMondo();
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_SALVATAGGIO));
            String id = doc.getElementsByTagName("idEventoAttuale").item(0).getTextContent();

            GameManager.getInstance().setPlayer(new Studente(
                    doc.getElementsByTagName("nome").item(0).getTextContent(),
                    Integer.parseInt(doc.getElementsByTagName("cfu").item(0).getTextContent()),
                    doc.getElementsByTagName("prossimoEsame").item(0).getTextContent()
            ));
            GameManager.getInstance().setEventoAttuale(mappaEventi.get(id));
        } catch (Exception e) { throw new IOException(e); }
    }

    private void addElement(Document doc, Element root, String tag, String val) {
        Element e = doc.createElement(tag); e.setTextContent(val); root.appendChild(e);
    }
}