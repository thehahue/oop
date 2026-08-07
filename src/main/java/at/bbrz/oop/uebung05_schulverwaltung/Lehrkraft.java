package at.bbrz.oop.uebung05_schulverwaltung;

public class Lehrkraft extends Schulperson {
    private final String fach;

    public Lehrkraft(int id, String name, String fach) {
        super(id, name);
        this.fach = fach;
    }

    public String getFach() {
        return fach;
    }

    @Override
    public String getRolle() {
        return "Lehrkraft fuer " + fach;
    }
}
