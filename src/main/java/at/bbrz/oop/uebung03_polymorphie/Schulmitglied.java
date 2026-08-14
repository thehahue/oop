package at.bbrz.oop.uebung03_polymorphie;

public abstract class Schulmitglied {
    private final String name;
    private final String personenNummer;

    protected Schulmitglied(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }

        this.name = name;
        this.personenNummer = PersonenNummernVerwaltung.nextPersonenNummer();

    }

    public String getName() {
        return name;
    }

    public String getPersonenNummer() {
        return personenNummer;
    }

    public abstract String gibTaetigkeit();

    public void beschreiben() {
        System.out.println(name + " " + gibTaetigkeit());
    }
}
