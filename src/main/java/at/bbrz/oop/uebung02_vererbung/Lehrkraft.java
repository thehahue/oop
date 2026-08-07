package at.bbrz.oop.uebung02_vererbung;

public class Lehrkraft extends Person {
    private final String fach;

    public Lehrkraft(String name, String fach) {
        super(name);
        this.fach = fach;
    }

    public String getFach() {
        return fach;
    }

    public void unterrichten() {
        System.out.println(getName() + " unterrichtet " + fach + ".");
    }
}
