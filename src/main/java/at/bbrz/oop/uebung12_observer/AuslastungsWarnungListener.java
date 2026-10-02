package at.bbrz.oop.uebung12_observer;

/**
 * Meldet, wenn die vorgegebene Kursauslastung erreicht ist.
 */
public class AuslastungsWarnungListener implements KursAnmeldungListener {
    private final double warnschwelle;

    public AuslastungsWarnungListener(double warnschwelle) {
        if (!Double.isFinite(warnschwelle)
                || warnschwelle <= 0.0
                || warnschwelle > 1.0) {
            throw new IllegalArgumentException(
                    "Die Warnschwelle muss groesser 0 und hoechstens 1 sein.");
        }
        this.warnschwelle = warnschwelle;
    }

    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {
        double auslastung =
                (double) event.belegtePlaetze() / event.maxTeilnehmende();
        if (auslastung >= warnschwelle) {
            System.out.printf(
                    "WARNUNG: %s ist zu %.0f %% ausgelastet (%d/%d).%n",
                    event.kursbezeichnung(),
                    auslastung * 100,
                    event.belegtePlaetze(),
                    event.maxTeilnehmende());
        }
    }

    @Override
    public void anmeldungFehlerhaft(KursAnmeldungEvent event) {

    }
}
