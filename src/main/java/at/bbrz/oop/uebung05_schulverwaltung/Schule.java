package at.bbrz.oop.uebung05_schulverwaltung;

import java.util.ArrayList;
import java.util.List;

public class Schule {
    private final String name;
    private final List<Schulperson> personen = new ArrayList<>();

    public Schule(String name) {
        this.name = name;
    }

    public void personAufnehmen(Schulperson person) {
        if (person != null) {
            personen.add(person);
        }
    }

    public Schulperson findePerson(int id) {
        for (Schulperson person : personen) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public void personenAusgeben() {
        System.out.println("Personen an der " + name + ":");
        for (Schulperson person : personen) {
            System.out.println("  " + person.getBeschreibung());
        }
    }
}
