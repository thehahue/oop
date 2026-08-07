package at.bbrz.oop.uebung02_vererbung;

public class Schueler extends Person {
    private final String klasse;

    public Schueler(String name, String klasse) {
        super(name);
        this.klasse = klasse;
    }

    public String getKlasse() {
        return klasse;
    }

    public void lernen() {
        System.out.println(getName() + " lernt fuer die Klasse " + klasse + ".");
    }
}
