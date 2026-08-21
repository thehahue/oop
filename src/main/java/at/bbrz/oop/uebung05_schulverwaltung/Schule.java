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
        if (person == null) {
            return;
        }

        if (isIdInPersonList(person.getId())) {
            throw new RuntimeException("Schulperson ID ist bereits vergeben");
        }

        if (isNameInPersonList(person.getName())) {
            throw new RuntimeException("Schulperson Name ist bereits vergeben");
        }

        personen.add(person);
    }

    public void direktorAufnehmen(Direktor direktor) {
        if (direktor == null) {
            return;
        }

        direktor.setSchule(this);

        personAufnehmen(direktor);
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

    private boolean isIdInPersonList(int searchId) {
        for (Schulperson person : personen) {
            if (person.getId() == searchId) {
                return true;
            }
        }

        return false;
    }

    private boolean isNameInPersonList(String searchName) {
        for (Schulperson person : personen) {
            if (person.getName().equals(searchName)) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }
}
