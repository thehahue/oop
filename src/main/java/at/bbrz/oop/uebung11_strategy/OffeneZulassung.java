package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;

/**
 * Erlaubt allen Schuelerinnen und Schuelern die Teilnahme.
 */
public class OffeneZulassung implements Zulassungsstrategie {
    @Override
    public Zulassungsentscheidung pruefen(
            Kursangebot kursangebot,
            Schueler schueler) {
        return Zulassungsentscheidung.erlaubt(
                "Der Kurs ist fuer alle Klassen offen.");
    }
}
