package at.bbrz.oop.uebung10_item_gallery;

import at.bbrz.oop.uebung09_polymorphie_ohne_switch.Backpack;
import at.bbrz.oop.uebung09_polymorphie_ohne_switch.Book;
import at.bbrz.oop.uebung09_polymorphie_ohne_switch.Laptop;
import at.bbrz.oop.uebung09_polymorphie_ohne_switch.Phone;
import at.bbrz.oop.uebung09_polymorphie_ohne_switch.SmartWatch;
import at.bbrz.oop.uebung09_polymorphie_ohne_switch.WaterBottle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.List;

public class Uebung10 {
    public static void main(String[] args) {
        Backpack backpack = new Backpack("Sonnenuntergang-Orange", 4, 0.80, 59.90, 5.00);

        SmartWatch smartWatch = new SmartWatch(
                "Garmin", 100, 10_500, 12_000, 0.05, 299.99);

        List<ItemMitBild> items = List.of(
                new ItemMitBild(
                        new Book("Der kleine Prinz", "Antoine de Saint-Exupery", 0.25, 12.90),
                        "/uebung10/book.png"),
                new ItemMitBild(
                        new Laptop("Lenovo", 75, 1.70, 899.00),
                        "/uebung10/laptop.png"),
                new ItemMitBild(
                        new WaterBottle("Edelstahl", 750, 0.35, 24.90),
                        "/uebung10/bottle.png"),
                new ItemMitBild(
                        new Phone("Google", "Pixel", 80, 0.19, 699.00),
                        "/uebung10/phone.png"),
                new ItemMitBild(smartWatch, "/uebung10/watch.png"));

        for (ItemMitBild item : items) {
            backpack.addItem(item);
        }

        ItemMitBild rucksackMitBild = new ItemMitBild(
                backpack, "/uebung10/backpack.png");

        SwingUtilities.invokeLater(() -> {
            UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(14f));
            RucksackFenster fenster = new RucksackFenster(
                    backpack, rucksackMitBild, items);
            fenster.setVisible(true);
        });
    }
}
