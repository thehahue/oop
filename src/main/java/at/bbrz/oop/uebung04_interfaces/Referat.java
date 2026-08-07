package at.bbrz.oop.uebung04_interfaces;

public class Referat extends Schulleistung {
    private final String thema;

    public Referat(String schuelerName, double note, String thema) {
        super(schuelerName, note);
        this.thema = thema;
    }

    @Override
    public String getArt() {
        return "Referat ueber " + thema;
    }
}
