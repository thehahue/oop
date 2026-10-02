package at.bbrz.oop.uebung12_observer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Zaehlt erfolgreiche Anmeldungen je Kurs.
 */
public class StatistikListener implements KursAnmeldungListener {
    private final Map<String, Integer> anmeldungenJeKurs = new LinkedHashMap<>();

    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {
        anmeldungenJeKurs.merge(event.kursbezeichnung(), 1, Integer::sum);
    }

    @Override
    public void anmeldungFehlerhaft(KursAnmeldungEvent event) {

    }

    public int getAnzahlAnmeldungen(String kursbezeichnung) {
        return anmeldungenJeKurs.getOrDefault(kursbezeichnung, 0);
    }

    public Map<String, Integer> getAnmeldungenJeKurs() {
        return Collections.unmodifiableMap(anmeldungenJeKurs);
    }
}
