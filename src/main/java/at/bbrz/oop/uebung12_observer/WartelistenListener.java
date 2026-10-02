package at.bbrz.oop.uebung12_observer;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Aktiviert fuer einen ausgebuchten Kurs eine simulierte Warteliste.
 */
public class WartelistenListener implements KursAnmeldungListener {
    private final Set<String> kurseMitWarteliste = new LinkedHashSet<>();

    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {

    }

    @Override
    public void anmeldungFehlerhaft(KursAnmeldungEvent event) {
        if (event.anmeldeErgebnisNachricht().contains("ist ausgebucht")) {
            kurseMitWarteliste.add(
                    "Schüler " + event.schuelerName()
                            + " mit der Id " + event.schuelerId()
                            + " aus der Klasse " + event.klasse()
                            + " wartet auf Kurs " + event.kursbezeichnung()
                            + " weil " + event.anmeldeErgebnisNachricht());

            System.out.printf(
                    "WARTELISTE: Fuer %s wurde die Warteliste aktiviert.%n",
                    event.kursbezeichnung());
        }
    }

    public Set<String> getKurseMitWarteliste() {
        return Collections.unmodifiableSet(kurseMitWarteliste);
    }
}
