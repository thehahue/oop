package at.bbrz.oop.uebung12_observer;

import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import java.util.Map;

public class FreieKurseListener implements KursAnmeldungListener {
    private final Schulverwaltung schulverwaltung;

    public FreieKurseListener(Schulverwaltung schulverwaltung) {
        if (schulverwaltung == null) {
            throw new IllegalArgumentException("Die Schulverwaltung darf nicht null sein.");
        }
        this.schulverwaltung = schulverwaltung;
    }

    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {
        // Bei erfolgreichen Anmeldungen ist keine Kursliste erforderlich.
    }

    @Override
    public void anmeldungFehlerhaft(KursAnmeldungEvent event) {
        System.out.printf(
                "FREIE KURSE fuer %s nach abgelehnter Anmeldung:%n",
                event.schuelerName());

        boolean freieKurseGefunden = false;
        for (Map.Entry<String, Kursangebot> eintrag
                : schulverwaltung.getKursangebote().entrySet()) {
            int freiePlaetze = eintrag.getValue().getFreiePlaetze();
            if (freiePlaetze > 0) {
                System.out.printf("- %s: %d freie Plaetze%n",
                        eintrag.getKey(), freiePlaetze);
                freieKurseGefunden = true;
            }
        }

        if (!freieKurseGefunden) {
            System.out.println("Zurzeit gibt es keine Kurse mit freien Plaetzen.");
        }
    }
}
