package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;

import java.util.List;

/**
 * Kombiniert mehrere Zulassungsstrategien, die alle zustimmen muessen.
 */
public class KombinierteZulassung implements Zulassungsstrategie {
    private final List<Zulassungsstrategie> strategien;

    public KombinierteZulassung(List<Zulassungsstrategie> strategien) {
        if (strategien == null || strategien.isEmpty()) {
            throw new IllegalArgumentException(
                    "Es muss mindestens eine Zulassungsstrategie angegeben werden.");
        }
        for (Zulassungsstrategie strategie : strategien) {
            if (strategie == null) {
                throw new IllegalArgumentException(
                        "Eine Zulassungsstrategie darf nicht null sein.");
            }
        }
        this.strategien = List.copyOf(strategien);
    }

    @Override
    public Zulassungsentscheidung pruefen(
            Kursangebot kursangebot,
            Schueler schueler) {
        for (Zulassungsstrategie strategie : strategien) {
            Zulassungsentscheidung entscheidung =
                    strategie.pruefen(kursangebot, schueler);
            if (!entscheidung.erlaubt()) {
                return Zulassungsentscheidung.abgelehnt(
                        strategie.getClass().getSimpleName() + ": "
                                + entscheidung.begruendung());
            }
        }

        return Zulassungsentscheidung.erlaubt(
                "Alle Zulassungsstrategien haben zugestimmt.");
    }
}
