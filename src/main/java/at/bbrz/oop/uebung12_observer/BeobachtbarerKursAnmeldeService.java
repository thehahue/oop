package at.bbrz.oop.uebung12_observer;

import at.bbrz.oop.uebung05_schulverwaltung.Schulperson;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import at.bbrz.oop.uebung11_strategy.Anmeldeergebnis;
import at.bbrz.oop.uebung11_strategy.KursAnmeldeService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Erweitert den Anmeldeservice aus Uebung 11 um fachliche Ereignisse.
 */
public class BeobachtbarerKursAnmeldeService {
    private final KursAnmeldeService anmeldeService;
    private final Schulverwaltung schulverwaltung;
    private final List<KursAnmeldungListener> listeners = new ArrayList<>();

    public BeobachtbarerKursAnmeldeService(
            KursAnmeldeService anmeldeService,
            Schulverwaltung schulverwaltung) {
        if (anmeldeService == null || schulverwaltung == null) {
            throw new IllegalArgumentException(
                    "Anmeldeservice und Schulverwaltung duerfen nicht null sein.");
        }
        this.anmeldeService = anmeldeService;
        this.schulverwaltung = schulverwaltung;
    }

    public void listenerHinzufuegen(KursAnmeldungListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Der Listener darf nicht null sein.");
        }
        listeners.add(listener);
    }

    public boolean listenerEntfernen(KursAnmeldungListener listener) {
        return listeners.remove(listener);
    }

    public Anmeldeergebnis anmelden(String kursbezeichnung, int schuelerId) {
        Anmeldeergebnis ergebnis =
                anmeldeService.anmelden(kursbezeichnung, schuelerId);

        if (ergebnis.erfolgreich()) {
            KursAnmeldungEvent event = erstelleEvent(kursbezeichnung, schuelerId);
            listenersInformieren(event);
        }
        return ergebnis;
    }

    private KursAnmeldungEvent erstelleEvent(
            String kursbezeichnung,
            int schuelerId) {
        Kursangebot kursangebot =
                schulverwaltung.getKursangebote().get(kursbezeichnung);
        Schulperson schueler =
                schulverwaltung.getSchule().findePerson(schuelerId);

        return new KursAnmeldungEvent(
                kursbezeichnung,
                schuelerId,
                schueler.getName(),
                kursangebot.getAnzahlTeilnehmende(),
                kursangebot.getMaxTeilnehmende(),
                Instant.now());
    }

    private void listenersInformieren(KursAnmeldungEvent event) {
        // Die Kopie erlaubt einem Listener, sich waehrend der Benachrichtigung
        // an- oder abzumelden, ohne die laufende Schleife zu beschaedigen.
        for (KursAnmeldungListener listener : List.copyOf(listeners)) {
            listener.anmeldungErfolgt(event);
        }
    }
}
