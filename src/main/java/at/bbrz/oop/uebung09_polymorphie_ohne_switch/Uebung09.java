package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.io.IOException;
import java.util.Optional;

public class Uebung09 {
    public static void main(String[] args) {
        Backpack backpack = new Backpack("blau", 4, 0.80, 59.90, 5.00);
        backpack.addItem(new Book(
                "Der kleine Prinz", "Antoine de Saint-Exupery", 0.25, 12.90));
        backpack.addItem(new Laptop("Lenovo", 75, 1.70, 899.00));
        backpack.addItem(new WaterBottle("Edelstahl", 750, 0.35, 24.90));
        backpack.addItem(new Phone("Google", "Pixel", 80, 0.19, 699.00));

        SmartWatch smartWatch = new SmartWatch(
                "Garmin", 15, 7_500, 20_000, 0.05, 299.99);
        smartWatch.recordSteps(3_000);
        smartWatch.charge();
        backpack.addItem(smartWatch);

        Backpack neuerBackback = new Backpack("Grün", 3, 1, 99, 4);
        neuerBackback.addItem(new Book("Harry Potter", "Harry Potter", 0.25, 12.90));

        backpack.addItem(neuerBackback);

        ItemJsonPersistenz persistenz = new ItemJsonPersistenz();
        try {
            persistenz.speichern(backpack);
            Backpack geladenerRucksack = persistenz.laden();

            System.out.println("Aus daten/uebung9.json geladen:");
            System.out.println(geladenerRucksack.inventoryOverview());

            Item derKleinePrinz = geladenerRucksack.getItems().getFirst();
            geladenerRucksack.removeItem(derKleinePrinz);
            System.out.println(geladenerRucksack.inventoryOverview());

        } catch (IOException exception) {
            System.out.println("Fehler beim Speichern oder Laden: "
                    + exception.getMessage());
        }

        // Fuer einen weiteren Gegenstand sind kein switch, kein instanceof und
        // keine Registrierung notwendig:
        // 1. Eine neue Klasse erstellen, die Item implementiert.
        // 2. Ein Objekt dieser Klasse zum Rucksack hinzufuegen.
        // Die Persistenz und die Schleife oben muessen nicht veraendert werden.

    }
}
