package at.bbrz.oop.uebung04_interfaces;

public class Klassenarbeit extends Schulleistung {
    private final String fach;

    public Klassenarbeit(String schuelerName, double note, String fach) {
        super(schuelerName, note);
        this.fach = fach;
    }

    @Override
    public String getArt() {
        return "Klassenarbeit in " + fach;
    }

    @Override
    public boolean geheim() {
        return false;
    }
}
