package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;

import java.util.Set;

/**
 * Erlaubt Klassen, deren Name mit einem der konfigurierten Praefixe beginnt.
 */
public class KlassenpraefixZulassung implements Zulassungsstrategie {
    private final Set<String> zugelassenePraefixe;

    public KlassenpraefixZulassung(Set<String> zugelassenePraefixe) {
        if (zugelassenePraefixe == null || zugelassenePraefixe.isEmpty()) {
            throw new IllegalArgumentException(
                    "Es muss mindestens ein Klassenpraefix angegeben werden.");
        }
        for (String praefix : zugelassenePraefixe) {
            if (praefix == null || praefix.isBlank()) {
                throw new IllegalArgumentException(
                        "Ein Klassenpraefix darf nicht leer sein.");
            }
        }
        this.zugelassenePraefixe = Set.copyOf(zugelassenePraefixe);
    }

    @Override
    public Zulassungsentscheidung pruefen(
            Kursangebot kursangebot,
            Schueler schueler) {
        for (String praefix : zugelassenePraefixe) {
            if (schueler.getKlasse().startsWith(praefix)) {
                return Zulassungsentscheidung.erlaubt(
                        "Die Klasse " + schueler.getKlasse()
                                + " beginnt mit einem zugelassenen Praefix.");
            }
        }

        return Zulassungsentscheidung.abgelehnt(
                "Die Klasse " + schueler.getKlasse()
                        + " beginnt mit keinem zugelassenen Praefix.");
    }

}
