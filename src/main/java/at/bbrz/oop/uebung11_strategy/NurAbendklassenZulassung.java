package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;

/**
 * Erlaubt nur Schuelerinnen und Schuelern aus einer Abendklasse die Teilnahme.
 */
public class NurAbendklassenZulassung implements Zulassungsstrategie {
    private static final String ABENDKLASSEN_PRAEFIX = "Abend-";

    @Override
    public Zulassungsentscheidung pruefen(
            Kursangebot kursangebot,
            Schueler schueler) {
        if (schueler.getKlasse().startsWith(ABENDKLASSEN_PRAEFIX)) {
            return Zulassungsentscheidung.erlaubt(
                    "Die Klasse " + schueler.getKlasse()
                            + " ist eine Abendklasse.");
        }

        return Zulassungsentscheidung.abgelehnt(
                "Die Klasse " + schueler.getKlasse()
                        + " ist keine Abendklasse.");
    }
}
