package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.util.List;

/**
 * Behaelter fuer alle Gegenstaende, die als JSON gespeichert werden.
 */
public record GameItems(List<Item> items) {
}
