package at.bbrz.oop.uebung02_vererbung;

public class Person {
    private final String name;

    public Person(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void vorstellen() {
        System.out.println("Ich heisse " + name + "!");
    }
}
