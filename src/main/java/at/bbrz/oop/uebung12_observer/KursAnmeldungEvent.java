package at.bbrz.oop.uebung12_observer;

import java.time.Instant;

/**
 * Unveraenderliche Nachricht ueber eine erfolgreich durchgefuehrte Anmeldung.
 */
public record KursAnmeldungEvent(
        String kursbezeichnung,
        int schuelerId,
        String schuelerName,
        int belegtePlaetze,
        int maxTeilnehmende,
        Instant zeitpunkt,
        String klasse,
        boolean anmeldungErfolgreich,
        String anmeldeErgebnisNachricht) {

    public KursAnmeldungEvent {
        if (kursbezeichnung == null || kursbezeichnung.isBlank()) {
            throw new IllegalArgumentException("Die Kursbezeichnung darf nicht leer sein.");
        }
        if (schuelerId <= 0) {
            throw new IllegalArgumentException("Die Schueler-ID muss positiv sein.");
        }
        if (schuelerName == null || schuelerName.isBlank()) {
            throw new IllegalArgumentException("Der Schuelername darf nicht leer sein.");
        }
        if (belegtePlaetze < 0
                || maxTeilnehmende <= 0
                || maxTeilnehmende < belegtePlaetze) {
            throw new IllegalArgumentException("Die Platzangaben sind ungueltig.");
        }
        if (zeitpunkt == null) {
            throw new IllegalArgumentException("Der Zeitpunkt darf nicht null sein.");
        }
        if (klasse == null || klasse.isBlank()) {
            throw new IllegalArgumentException("Die Klasse darf nicht leer sein.");
        }
        if (anmeldeErgebnisNachricht == null
                || anmeldeErgebnisNachricht.isBlank()) {
            throw new IllegalArgumentException(
                    "Die Ergebnisnachricht darf nicht leer sein.");
        }
    }
}
