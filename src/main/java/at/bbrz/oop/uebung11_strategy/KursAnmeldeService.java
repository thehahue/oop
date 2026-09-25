package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.Schulperson;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

/**
 * Backend-Service fuer den Anwendungsfall einer Kursanmeldung.
 *
 * Die Zulassungsstrategie wird ueber den Konstruktor uebergeben. Das ist die
 * gleiche Form der Dependency Injection, die spaeter auch Spring verwendet.
 */
public class KursAnmeldeService {
    private final Schulverwaltung schulverwaltung;
    private final Zulassungsstrategie zulassungsstrategie;

    public KursAnmeldeService(
            Schulverwaltung schulverwaltung,
            Zulassungsstrategie zulassungsstrategie) {
        if (schulverwaltung == null || zulassungsstrategie == null) {
            throw new IllegalArgumentException(
                    "Schulverwaltung und Zulassungsstrategie duerfen nicht null sein.");
        }
        this.schulverwaltung = schulverwaltung;
        this.zulassungsstrategie = zulassungsstrategie;
    }

    public Anmeldeergebnis anmelden(String kursbezeichnung, int schuelerId) {
        Kursangebot kursangebot = findeKurs(kursbezeichnung);
        Schueler schueler = findeSchueler(schuelerId);

        // Diese Regeln gelten immer und sind deshalb keine Strategy.
        boolean bereitsAngemeldet = kursangebot.getKurs().getTeilnehmende().stream()
                .anyMatch(teilnehmer -> teilnehmer.getId() == schuelerId);
        if (bereitsAngemeldet) {
            return Anmeldeergebnis.abgelehnt(
                    schueler.getName() + " ist bereits angemeldet.");
        }
        if (kursangebot.istAusgebucht()) {
            return Anmeldeergebnis.abgelehnt(
                    "Der Kurs " + kursbezeichnung + " ist ausgebucht.");
        }

        Zulassungsentscheidung entscheidung =
                zulassungsstrategie.pruefen(kursangebot, schueler);
        if (!entscheidung.erlaubt()) {
            return Anmeldeergebnis.abgelehnt(entscheidung.begruendung());
        }

        boolean angemeldet = kursangebot.anmelden(schueler);
        if (!angemeldet) {
            return Anmeldeergebnis.abgelehnt(
                    "Die Anmeldung konnte nicht durchgefuehrt werden.");
        }

        return Anmeldeergebnis.erfolgreich(
                schueler.getName() + " wurde zu " + kursbezeichnung + " angemeldet.");
    }

    private Kursangebot findeKurs(String kursbezeichnung) {
        if (kursbezeichnung == null || kursbezeichnung.isBlank()) {
            throw new IllegalArgumentException("Die Kursbezeichnung darf nicht leer sein.");
        }

        Kursangebot kursangebot =
                schulverwaltung.getKursangebote().get(kursbezeichnung);
        if (kursangebot == null) {
            throw new IllegalArgumentException(
                    "Kurs nicht gefunden: " + kursbezeichnung);
        }
        return kursangebot;
    }

    private Schueler findeSchueler(int schuelerId) {
        Schulperson person = schulverwaltung.getSchule().findePerson(schuelerId);
        if (person == null) {
            throw new IllegalArgumentException(
                    "Keine Schulperson mit ID " + schuelerId + " gefunden.");
        }
        if (!(person instanceof Schueler schueler)) {
            throw new IllegalArgumentException(
                    "Die Person ist kein Schueler: " + schuelerId);
        }
        return schueler;
    }
}
