package at.bbrz.oop.uebung05_schulverwaltung;

public class Direktor extends Schulperson {
    private Schule schule;

    public Direktor(int id, String name, Schule schule) {
        super(id, name);
        this.schule = schule;
    }

    public Direktor(int id, String name) {
        super(id, name);
    }

    public void setSchule(Schule schule) {
        this.schule = schule;
    }

    @Override
    public String getRolle() {
        return "Leitet die Schule " + schule.getName();
    }

}
