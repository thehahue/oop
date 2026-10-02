package at.bbrz.oop.uebung12_observer;

/**
 * Observer fuer erfolgreiche Kursanmeldungen.
 */
public interface KursAnmeldungListener {
    void anmeldungErfolgt(KursAnmeldungEvent event);
    void anmeldungFehlerhaft(KursAnmeldungEvent event);
}
