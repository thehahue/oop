package at.bbrz.oop.uebung01_datenkapselung;

public class Schueler {
    private String name;
    private int alter;

    public Schueler(String name, int alter) {
        setName(name);
        setAlter(alter);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        this.name = name;
    }

    public int getAlter() {
        return alter;
    }

    private void setAlter(int alter) {
        if (alter < 5 || alter > 100) {
            throw new IllegalArgumentException("Das Alter muss zwischen 5 und 100 liegen.");
        }
        this.alter = alter;
    }

    public void vorstellen() {
        System.out.printf("Hallo, ich bin %s und %d Jahre alt.%n", name, alter);
    }

    // TODO: Fuege das private Feld klasse samt Getter und geprueftem Setter hinzu.
}
