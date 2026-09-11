package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Uebung09 {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Book(
                "Der kleine Prinz", "Antoine de Saint-Exupery", 0.25, 12.90));
        items.add(new Laptop("Lenovo", 75, 1.70, 899.00));
        items.add(new Backpack("blau", 4, 0.80, 59.90));
        items.add(new WaterBottle("Edelstahl", 750, 0.35, 24.90));
        items.add(new Phone("Google", "Pixel", 80, 0.19, 699.00));

        SmartWatch smartWatch = new SmartWatch(
                "Garmin", 15, 7_500, 20_000, 0.05, 299.99);
        smartWatch.recordSteps(3_000);
        smartWatch.charge();
        items.add(smartWatch);

        GameItems gameItems = new GameItems(items);
        ItemJsonPersistenz persistenz = new ItemJsonPersistenz();
        try {
            persistenz.speichern(gameItems);
            GameItems geladeneGameItems = persistenz.laden();

            System.out.println("Aus daten/uebung9.json geladen:");
            double gesamtgewicht = 0;
            double gesamtpreis = 0;
            for (Item item : geladeneGameItems.items()) {
                System.out.printf("- %s (%.2f kg, %.2f EUR)%n",
                        item.getDescription(), item.weightInKg(), item.priceInEur());
                gesamtgewicht += item.weightInKg();
                gesamtpreis += item.priceInEur();
            }
            System.out.printf("Gesamt: %.2f kg, %.2f EUR%n",
                    gesamtgewicht, gesamtpreis);
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
