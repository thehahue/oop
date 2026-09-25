package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;

import java.util.Set;

/**
 * Erlaubt nur Schuelerinnen und Schuelern bestimmter Klassen die Teilnahme.
 */
public class KlassenZulassung implements Zulassungsstrategie {
    private final Set<String> zugelasseneKlassen;

    public KlassenZulassung(Set<String> zugelasseneKlassen) {
        if (zugelasseneKlassen == null || zugelasseneKlassen.isEmpty()) {
            throw new IllegalArgumentException(
                    "Es muss mindestens eine Klasse zugelassen sein.");
        }
        if (zugelasseneKlassen.stream()
                .anyMatch(klasse -> klasse == null || klasse.isBlank())) {
            throw new IllegalArgumentException(
                    "Eine zugelassene Klasse darf nicht leer sein.");
        }
        this.zugelasseneKlassen = Set.copyOf(zugelasseneKlassen);
    }

    @Override
    public Zulassungsentscheidung pruefen(
            Kursangebot kursangebot,
            Schueler schueler) {
        if (zugelasseneKlassen.contains(schueler.getKlasse())) {
            return Zulassungsentscheidung.erlaubt(
                    "Die Klasse " + schueler.getKlasse() + " ist zugelassen.");
        }

        return Zulassungsentscheidung.abgelehnt(
                "Die Klasse " + schueler.getKlasse()
                        + " ist fuer diesen Kurs nicht zugelassen.");
    }

    public Set<String> getZugelasseneKlassen() {
        return zugelasseneKlassen;
    }
}
