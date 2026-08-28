package at.bbrz.oop.uebung06_kursverwaltung;

import at.bbrz.oop.uebung05_schulverwaltung.Kurs;
import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung05_schulverwaltung.Schulperson;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Verbindet die Klassen aus Uebung 5 zu einer zentralen Kursverwaltung.
 */
public class Schulverwaltung {
    private final Schule schule;
    private final Map<String, Kursangebot> kursangebote = new LinkedHashMap<>();

    public Schulverwaltung(Schule schule) {
        if (schule == null) {
            throw new IllegalArgumentException("Die Schule darf nicht null sein.");
        }
        this.schule = schule;
    }

    public void kursAnlegen(String bezeichnung, int lehrkraftId, int maxTeilnehmende) {
        if (bezeichnung == null || bezeichnung.isBlank()) {
            throw new IllegalArgumentException("Die Kursbezeichnung darf nicht leer sein.");
        }
        if (kursangebote.containsKey(bezeichnung)) {
            throw new IllegalArgumentException("Der Kurs existiert bereits: " + bezeichnung);
        }

        Schulperson person = findePersonOderFehler(lehrkraftId);
        if (!(person instanceof Lehrkraft lehrkraft)) {
            throw new IllegalArgumentException("Die Person ist keine Lehrkraft: " + lehrkraftId);
        }

        Kurs kurs = new Kurs(bezeichnung, lehrkraft);
        kursangebote.put(bezeichnung, new Kursangebot(kurs, maxTeilnehmende));
    }

    public boolean schuelerAnmelden(String kursbezeichnung, int schuelerId) {
        Kursangebot kursangebot = findeKursOderFehler(kursbezeichnung);
        Schulperson person = findePersonOderFehler(schuelerId);
        if (!(person instanceof Schueler schueler)) {
            throw new IllegalArgumentException("Die Person ist kein Schueler: " + schuelerId);
        }

        return kursangebot.anmelden(schueler);
    }

    public boolean schuelerAbmelden(String kursbezeichnung, int schuelerId) {
        return findeKursOderFehler(kursbezeichnung).abmelden(schuelerId);
    }

    public void kursangeboteAusgeben() {
        System.out.println("Kursangebote der " + schule.getName() + ":");
        for (Kursangebot kursangebot : kursangebote.values()) {
            kursangebot.ausgeben();
            System.out.println("  Freie Plaetze: " + kursangebot.getFreiePlaetze());
        }
    }

    public void ausgebuchteKurseAusgeben() {
        System.out.println("Ausgebuchte Kurse der " + schule.getName() + ":");
        for (Kursangebot kursangebot : kursangebote.values()) {
            if (kursangebot.istAusgebucht()) {
                kursangebot.ausgeben();
            }
        }
    }

    private Schulperson findePersonOderFehler(int id) {
        Schulperson person = schule.findePerson(id);
        if (person == null) {
            throw new IllegalArgumentException("Keine Schulperson mit ID " + id + " gefunden.");
        }
        return person;
    }

    private Kursangebot findeKursOderFehler(String bezeichnung) {
        Kursangebot kursangebot = kursangebote.get(bezeichnung);
        if (kursangebot == null) {
            throw new IllegalArgumentException("Kurs nicht gefunden: " + bezeichnung);
        }
        return kursangebot;
    }
}
