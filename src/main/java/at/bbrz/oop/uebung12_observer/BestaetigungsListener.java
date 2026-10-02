package at.bbrz.oop.uebung12_observer;

/**
 * Simuliert den Versand einer Anmeldebestaetigung.
 */
public class BestaetigungsListener implements KursAnmeldungListener {
    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {
        System.out.printf(
                "BESTAETIGUNG: Hallo %s, du bist fuer %s angemeldet.%n",
                event.schuelerName(),
                event.kursbezeichnung());
    }
}
