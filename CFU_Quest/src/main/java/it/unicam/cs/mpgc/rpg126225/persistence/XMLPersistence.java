package it.unicam.cs.mpgc.rpg126225.persistence;

import it.unicam.cs.mpgc.rpg126225.model.*;
import it.unicam.cs.mpgc.rpg126225.model.eventi.*;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Player;
import it.unicam.cs.mpgc.rpg126225.model.giocatore.Studente;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Implementazione concreta dell'interfaccia Persistence basata sul formato XML.
 *
 * @author Andrea Mecozzi
 */
public class XMLPersistence implements Persistence {

    private final String PATH_STATIC = "src/main/resources/persistence/";
    private final String FILE_SALVATAGGIO = PATH_STATIC + "salvataggio.xml";
    private final String FILE_EVENTI = PATH_STATIC + "eventi.xml";
    private final String FILE_OPZIONI = PATH_STATIC + "opzioni.xml";

    private final Map<String, Evento> cacheEventi = new HashMap<>();

    /**
     * Costruttore di default.
     */
    public XMLPersistence() {}

    /**
     * Inizializza una nuova partita resettando lo stato nel GameManager e
     * sovrascrivendo il precedente file di salvataggio.
     * @param playerName Il nome dello studente inserito dall'utente.
     */
    @Override
    public void nuovaPartita(String playerName) {
        GameManager gm = GameManager.getInstance();

        Player p = new Studente(playerName);
        p.aggiungiCfu(0);
        p.cambiaProssimoEsame("Programmazione");
        gm.setPlayer(p);

        gm.setEventoAttuale(getEvento("EVT_INIZIO_01"));

        try {
            salvaPartita();
        } catch (IOException e) {
            System.err.println("Errore durante la creazione del file di salvataggio: " + e.getMessage());
        }
    }

    /**
     * Carica i dati dal file di salvataggio e ripristina la sessione
     * all'interno del GameManager
     * @throws IOException Se il file di salvataggio è mancante, illeggibile o corrotto.
     */
    @Override
    public void caricaPartita() throws IOException {
        Document doc = loadDocument(FILE_SALVATAGGIO);
        if (doc == null) throw new IOException("File di salvataggio non trovato.");

        String nome = doc.getElementsByTagName("nome").item(0).getTextContent();
        int cfu = Integer.parseInt(doc.getElementsByTagName("cfu").item(0).getTextContent());
        String prossimoEsame = doc.getElementsByTagName("prossimoEsame").item(0).getTextContent();
        String idEventoAttuale = doc.getElementsByTagName("idEventoAttuale").item(0).getTextContent();

        GameManager gm = GameManager.getInstance();

        Player p = new Studente(nome);
        p.aggiungiCfu(cfu);
        p.cambiaProssimoEsame(prossimoEsame);
        gm.setPlayer(p);

        gm.setEventoAttuale(getEvento(idEventoAttuale));
    }

    /**
     * Serializza l'attuale stato del GameManager in formato XML per il salvataggio
     * della partita
     * @throws IOException Se si verificano errori nei permessi di scrittura o durante
     * la trasformazione dell'albero DOM.
     */
    @Override
    public void salvaPartita() throws IOException {
        GameManager gm = GameManager.getInstance();
        Player p = gm.getPlayer();
        Evento attuale = gm.getEventoAttuale();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("salvataggio");
            doc.appendChild(rootElement);

            Element nome = doc.createElement("nome");
            nome.appendChild(doc.createTextNode(p.getNome()));
            rootElement.appendChild(nome);

            Element cfu = doc.createElement("cfu");
            cfu.appendChild(doc.createTextNode(String.valueOf(p.cfuAccumulati())));
            rootElement.appendChild(cfu);

            Element prossimo = doc.createElement("prossimoEsame");
            prossimo.appendChild(doc.createTextNode(p.prossimoEsame()));
            rootElement.appendChild(prossimo);

            Element idEvento = doc.createElement("idEventoAttuale");
            idEvento.appendChild(doc.createTextNode(attuale.getId()));
            rootElement.appendChild(idEvento);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_SALVATAGGIO));
            transformer.transform(source, result);

        } catch (Exception e) {
            throw new IOException("Errore durante il salvataggio XML: " + e.getMessage());
        }
    }

    /**
     * Recupera l'oggetto Evento
     * @param idEvento La stringa univoca che identifica l'evento da caricare.
     * @return L'oggetto Evento pienamente popolato con le relative opzioni, null altrimenti
     */
    @Override
    public Evento getEvento(String idEvento){
        if (cacheEventi.containsKey(idEvento)) {
            return cacheEventi.get(idEvento);
        }

        Document doc = loadDocument(FILE_EVENTI);
        if (doc == null) return null;

        NodeList nList = doc.getElementsByTagName("evento");
        for (int i = 0; i < nList.getLength(); i++) {
            Element e = (Element) nList.item(i);

            if (e.getAttribute("id").equals(idEvento)) {
                String tipo = e.getAttribute("tipo");
                String descrizione = e.getElementsByTagName("descrizione").item(0).getTextContent();

                List<String> idScelte = new ArrayList<>();
                NodeList scelteNodes = e.getElementsByTagName("scelta");
                for (int j = 0; j < scelteNodes.getLength(); j++) {
                    idScelte.add(scelteNodes.item(j).getTextContent());
                }

                List<Opzione> opzioni = getOpzioni(idScelte);

                Evento eventoCreato;
                if ("ESAME".equals(tipo)) {
                    int cfu = Integer.parseInt(e.getElementsByTagName("cfu").item(0).getTextContent());
                    String prossimoEsame = e.getElementsByTagName("prossimoEsame").item(0).getTextContent();
                    String idCorretta = e.getElementsByTagName("idSceltaCorretta").item(0).getTextContent();

                    eventoCreato = new EventoEsame(idEvento, descrizione, opzioni, idCorretta, cfu, prossimoEsame);
                } else {
                    eventoCreato = new EventoStoria(idEvento, descrizione, opzioni);
                }

                cacheEventi.put(idEvento, eventoCreato);
                return eventoCreato;
            }
        }
        return null;
    }

    /**
     * Recupera una lista di Opzione associate ad un determinato evento.
     * @param idOpzioni La lista degli identificativi delle scelte da recuperare.
     * @return Una lista di Opzione estratti dal file XML.
     */
    @Override
    public List<Opzione> getOpzioni(List<String> idOpzioni) {
        List<Opzione> opzioniTrovate = new ArrayList<>();
        Document doc = loadDocument(FILE_OPZIONI);
        if (doc == null) return opzioniTrovate;

        NodeList nList = doc.getElementsByTagName("opzione");

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String id = eElement.getAttribute("id");

                if (idOpzioni.contains(id)) {
                    String testo = eElement.getElementsByTagName("testo").item(0).getTextContent();
                    String idProssimoEvento = eElement.getElementsByTagName("idProssimoEvento").item(0).getTextContent();

                    opzioniTrovate.add(new Opzione(id, testo, idProssimoEvento));
                }
            }
        }
        return opzioniTrovate;
    }

    /**
     * Metodo di supporto privato per caricare in memoria l'albero DOM di un file XML.
     * @param filePath Il percorso relativo del file da processare.
     * @return Il documento XML istanziato, null altrimenti
     */
    private Document loadDocument(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica se esiste un file di salvataggio precedentemente creato.
     *
     * @return true se il file di salvataggio esiste nel percorso specificato,
     *         false altrimenti
     */
    @Override
    public boolean verificaEsistenzaSalvataggio() {
        return new java.io.File(FILE_SALVATAGGIO).exists();
    }
}