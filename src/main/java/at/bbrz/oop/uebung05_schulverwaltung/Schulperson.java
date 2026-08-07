package at.bbrz.oop.uebung05_schulverwaltung;

public abstract class Schulperson {
    private final int id;
    private final String name;

    protected Schulperson(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Die ID muss positiv sein.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getRolle();

    public String getBeschreibung() {
        return "%d - %s (%s)".formatted(id, name, getRolle());
    }
}
