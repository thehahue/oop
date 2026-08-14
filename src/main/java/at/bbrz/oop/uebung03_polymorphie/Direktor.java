package at.bbrz.oop.uebung03_polymorphie;

public class Direktor extends Schulmitglied {
    public Direktor(String name) {
        super(name);
    }

    @Override
    public String gibTaetigkeit() {
        return "leitet die Schule.";
    }
}
