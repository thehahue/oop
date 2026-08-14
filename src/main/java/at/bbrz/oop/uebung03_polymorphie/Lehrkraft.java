package at.bbrz.oop.uebung03_polymorphie;

public class Lehrkraft extends Schulmitglied {
    public Lehrkraft(String name) {
        super(name);
    }

    @Override
    public String gibTaetigkeit() {
        return "unterrichtet und korrigiert Aufgaben.";
    }
}
