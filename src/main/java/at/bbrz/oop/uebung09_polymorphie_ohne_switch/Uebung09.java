package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Uebung09 {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Book("Der kleine Prinz", "Antoine de Saint-Exupery"));
        items.add(new Laptop("Lenovo", 75));
        items.add(new Backpack("blau", 4));
        items.add(new WaterBottle("Edelstahl", 750));

        GameItems gameItems = new GameItems(items);
        ItemJsonPersistenz persistenz = new ItemJsonPersistenz();
        try {
            persistenz.speichern(gameItems);
            GameItems geladeneGameItems = persistenz.laden();

            System.out.println("Aus daten/uebung9.json geladen:");
            for (Item item : geladeneGameItems.items()) {
                System.out.println(item.getDescription());
            }
        } catch (IOException exception) {
            System.out.println("Fehler beim Speichern oder Laden: "
                    + exception.getMessage());
        }

        // Fuer einen weiteren Gegenstand sind kein switch, kein instanceof und
        // keine Registrierung notwendig:
        // 1. Eine neue Klasse erstellen, die Item implementiert.
        // 2. Ein Objekt dieser Klasse zu items hinzufuegen.
        // Die Persistenz und die Schleife oben muessen nicht veraendert werden.

    }
}
