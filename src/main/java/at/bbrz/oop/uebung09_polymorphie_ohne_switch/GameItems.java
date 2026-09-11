package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.util.ArrayList;
import java.util.List;

/**
 * Behaelter fuer alle Gegenstaende, die als JSON gespeichert werden.
 */
public class GameItems {
    private List<Item> items = new ArrayList<>();

    public GameItems() {
    }

    public GameItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
