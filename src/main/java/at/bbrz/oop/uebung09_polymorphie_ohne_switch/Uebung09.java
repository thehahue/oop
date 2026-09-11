package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.util.ArrayList;
import java.util.List;

public class Uebung09 {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Book("Der kleine Prinz", "Antoine de Saint-Exupery"));
        items.add(new Laptop("Lenovo", 75));
        items.add(new Backpack("blau", 4));

        for (Item item : items) {
            System.out.println(item.getDescription());
        }

        // Fuer einen weiteren Gegenstand sind kein switch, kein instanceof und
        // keine Registrierung notwendig:
        // 1. Eine neue Klasse erstellen, die Item implementiert.
        // 2. Ein Objekt dieser Klasse zu items hinzufuegen.
        // Die Schleife oben muss nicht veraendert werden.

        // TODO: Erstelle die Klasse WaterBottle, die Item implementiert.
        // TODO: Fuege eine WaterBottle zur Liste hinzu.
    }
}
