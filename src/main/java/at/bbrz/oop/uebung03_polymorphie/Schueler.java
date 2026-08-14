package at.bbrz.oop.uebung03_polymorphie;

public class Schueler extends Schulmitglied {
    public Schueler(String name) {
        super(name);
    }

    @Override
    public String gibTaetigkeit() {
        return "lernt und erledigt Hausaufgaben";
    }
}
