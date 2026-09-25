package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;

/**
 * Austauschbare Geschaeftsregel fuer die Zulassung zu einem Kurs.
 */
public interface Zulassungsstrategie {
    Zulassungsentscheidung pruefen(Kursangebot kursangebot, Schueler schueler);
}
