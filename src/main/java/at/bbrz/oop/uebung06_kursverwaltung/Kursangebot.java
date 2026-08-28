package at.bbrz.oop.uebung06_kursverwaltung;

import at.bbrz.oop.uebung05_schulverwaltung.Kurs;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;

/**
 * Ergaenzt einen Kurs aus Uebung 5 um eine begrenzte Teilnehmerzahl.
 */
public class Kursangebot {
    private final Kurs kurs;
    private final int maxTeilnehmende;

    public Kursangebot(Kurs kurs, int maxTeilnehmende) {
        if (kurs == null) {
            throw new IllegalArgumentException("Das Kursangebot braucht einen Kurs.");
        }
        if (maxTeilnehmende <= 0) {
            throw new IllegalArgumentException("Die Teilnehmerzahl muss positiv sein.");
        }
        this.kurs = kurs;
        this.maxTeilnehmende = maxTeilnehmende;
    }

    public boolean anmelden(Schueler schueler) {
        if (schueler == null) {
            throw new IllegalArgumentException("Der Schueler darf nicht null sein.");
        }
        if (kurs.getTeilnehmende().contains(schueler) || istAusgebucht()) {
            return false;
        }

        kurs.anmelden(schueler);
        return true;
    }

    public boolean abmelden(int schuelerId) {
        int anzahlVorher = getAnzahlTeilnehmende();
        kurs.abmelden(schuelerId);
        return getAnzahlTeilnehmende() < anzahlVorher;
    }

    public int getAnzahlTeilnehmende() {
        return kurs.getTeilnehmende().size();
    }

    public int getMaxTeilnehmende() {
        return maxTeilnehmende;
    }

    public boolean istAusgebucht() {
        return getAnzahlTeilnehmende() >= maxTeilnehmende;
    }

    public void ausgeben() {
        kurs.ausgeben();
        System.out.printf("  Plaetze: %d/%d (%s)%n",
                getAnzahlTeilnehmende(), maxTeilnehmende,
                istAusgebucht() ? "ausgebucht" : "verfuegbar");
    }
}
