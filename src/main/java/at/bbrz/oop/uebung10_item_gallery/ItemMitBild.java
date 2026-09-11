package at.bbrz.oop.uebung10_item_gallery;

import at.bbrz.oop.uebung09_polymorphie_ohne_switch.Item;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

/**
 * Ergaenzt ein beliebiges Item per Wrapper um ein Bild, ohne die Item-Klasse
 * selbst veraendern zu muessen.
 */
public final class ItemMitBild implements Item {
    private final Item item;
    private final ImageIcon originalBild;

    public ItemMitBild(Item item, String bildPfad) {
        if (item == null) {
            throw new IllegalArgumentException("Das Item darf nicht null sein.");
        }
        if (bildPfad == null || bildPfad.isBlank()) {
            throw new IllegalArgumentException("Der Bildpfad darf nicht leer sein.");
        }

        URL bildUrl = ItemMitBild.class.getResource(bildPfad);
        if (bildUrl == null) {
            throw new IllegalArgumentException("Bild nicht gefunden: " + bildPfad);
        }

        this.item = item;
        this.originalBild = new ImageIcon(bildUrl);
    }

    public Item getItem() {
        return item;
    }

    public ImageIcon getBild(int breite, int hoehe) {
        Image skaliertesBild = originalBild.getImage().getScaledInstance(
                breite, hoehe, Image.SCALE_SMOOTH);
        return new ImageIcon(skaliertesBild);
    }

    @Override
    public double weightInKg() {
        return item.weightInKg();
    }

    @Override
    public double priceInEur() {
        return item.priceInEur();
    }

    @Override
    public String getDescription() {
        return item.getDescription();
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
