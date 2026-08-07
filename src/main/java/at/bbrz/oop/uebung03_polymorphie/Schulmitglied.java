package at.bbrz.oop.uebung03_polymorphie;

public abstract class Schulmitglied {
    private final String name;

    protected Schulmitglied(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String gibTaetigkeit();

    public void beschreiben() {
        System.out.println(name + ": " + gibTaetigkeit());
    }
}
