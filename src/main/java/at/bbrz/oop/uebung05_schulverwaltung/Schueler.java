package at.bbrz.oop.uebung05_schulverwaltung;

public class Schueler extends Schulperson {
    private final String klasse;

    public Schueler(int id, String name, String klasse) {
        super(id, name);
        this.klasse = klasse;
    }

    public String getKlasse() {
        return klasse;
    }

    @Override
    public String getRolle() {
        return "Schueler/in der " + klasse;
    }
}
