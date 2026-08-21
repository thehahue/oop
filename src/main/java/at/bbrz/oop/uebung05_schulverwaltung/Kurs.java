package at.bbrz.oop.uebung05_schulverwaltung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kurs {
    private final String bezeichnung;
    private final Lehrkraft lehrkraft;
    private final List<Schueler> teilnehmende = new ArrayList<>();

    public Kurs(String bezeichnung, Lehrkraft lehrkraft) {
        if (bezeichnung == null || bezeichnung.isBlank()) {
            throw new IllegalArgumentException("Die Bezeichnung darf nicht leer sein.");
        }
        if (lehrkraft == null) {
            throw new IllegalArgumentException("Ein Kurs braucht eine Lehrkraft.");
        }
        this.bezeichnung = bezeichnung;
        this.lehrkraft = lehrkraft;
    }

    public void anmelden(Schueler schueler) {
        if (schueler == null || teilnehmende.contains(schueler)) {
            return;
        }
        teilnehmende.add(schueler);
    }

    public void abmelden(int suchId) {
        for (Schueler schueler : teilnehmende) {
            if (schueler.getId() == suchId) {
                teilnehmende.remove(schueler);
                return;
            }
        }
    }

    public List<Schueler> getTeilnehmende() {
        return Collections.unmodifiableList(teilnehmende);
    }

    public void ausgeben() {
        System.out.printf("%s mit %s (%d Teilnehmende)%n",
                bezeichnung, lehrkraft.getName(), teilnehmende.size());
        for (Schueler schueler : teilnehmende) {
            System.out.println("  - " + schueler.getBeschreibung());
        }
    }
}
