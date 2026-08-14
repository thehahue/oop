package at.bbrz.oop.uebung03_polymorphie;

public class Hausmeister extends Schulmitglied {
    public Hausmeister(String name) {
        super(name);
    }

    @Override
    public String gibTaetigkeit() {
        return getName() + " kuemmert sich um das Schulgebaeude";
    }
}
