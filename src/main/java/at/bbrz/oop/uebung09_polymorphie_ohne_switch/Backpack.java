package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Backpack implements Item {
    private String color;
    private int numberOfCompartments;
    private double emptyWeightInKg;
    private double ownPriceInEur;
    private double maxWeightInKg;
    private List<Item> items = new ArrayList<>();

    public Backpack() {
    }

    public Backpack(
            String color,
            int numberOfCompartments,
            double emptyWeightInKg,
            double ownPriceInEur,
            double maxWeightInKg) {
        setColor(color);
        setNumberOfCompartments(numberOfCompartments);
        setEmptyWeightInKg(emptyWeightInKg);
        setOwnPriceInEur(ownPriceInEur);
        setMaxWeightInKg(maxWeightInKg);
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Das Item darf nicht null sein.");
        }
        if (item == this) {
            throw new IllegalArgumentException(
                    "Der Rucksack kann sich nicht selbst enthalten.");
        }
        if (contentWeightInKg() + item.weightInKg() > maxWeightInKg) {
            throw new IllegalArgumentException(
                    "Das maximale Inhaltsgewicht wird ueberschritten.");
        }
        items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public double contentWeightInKg() {
        return items.stream()
                .mapToDouble(Item::weightInKg)
                .sum();
    }

    public double contentPriceInEur() {
        return items.stream()
                .mapToDouble(Item::priceInEur)
                .sum();
    }

    public double remainingCapacityInKg() {
        return maxWeightInKg - contentWeightInKg();
    }

    @Override
    public double weightInKg() {
        return emptyWeightInKg + contentWeightInKg();
    }

    @Override
    public double priceInEur() {
        return ownPriceInEur + contentPriceInEur();
    }

    @Override
    public String getDescription() {
        return "Rucksack in " + color + " mit " + numberOfCompartments
                + " Faechern";
    }

    public String inventoryOverview() {
        StringBuilder inhalt = new StringBuilder();
        for (Item item : items) {
            inhalt.append("  - ")
                    .append(item.getDescription())
                    .append(" (%.2f kg, %.2f EUR)".formatted(
                            item.weightInKg(), item.priceInEur()))
                    .append(System.lineSeparator());
        }
        if (items.isEmpty()) {
            inhalt.append("  (leer)").append(System.lineSeparator());
        }

        return ("""
                %s
                ----------------------------------------
                Inhalt:
                %s----------------------------------------
                Leergewicht:             %6.2f kg
                Gewicht des Inhalts:     %6.2f kg
                Verbleibende Kapazitaet: %6.2f kg
                Gesamtgewicht:           %6.2f kg
                Wert des Rucksacks:      %7.2f EUR
                Wert des Inhalts:        %7.2f EUR
                Gesamtwert:              %7.2f EUR
                """).formatted(
                getDescription(),
                inhalt,
                emptyWeightInKg,
                contentWeightInKg(),
                remainingCapacityInKg(),
                weightInKg(),
                ownPriceInEur,
                contentPriceInEur(),
                priceInEur());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Die Farbe darf nicht leer sein.");
        }
        this.color = color;
    }

    public int getNumberOfCompartments() {
        return numberOfCompartments;
    }

    public void setNumberOfCompartments(int numberOfCompartments) {
        if (numberOfCompartments <= 0) {
            throw new IllegalArgumentException(
                    "Die Anzahl der Faecher muss positiv sein.");
        }
        this.numberOfCompartments = numberOfCompartments;
    }

    public double getEmptyWeightInKg() {
        return emptyWeightInKg;
    }

    public void setEmptyWeightInKg(double emptyWeightInKg) {
        if (!Double.isFinite(emptyWeightInKg) || emptyWeightInKg <= 0) {
            throw new IllegalArgumentException(
                    "Das Leergewicht muss positiv sein.");
        }
        this.emptyWeightInKg = emptyWeightInKg;
    }

    public double getOwnPriceInEur() {
        return ownPriceInEur;
    }

    public void setOwnPriceInEur(double ownPriceInEur) {
        if (!Double.isFinite(ownPriceInEur) || ownPriceInEur < 0) {
            throw new IllegalArgumentException(
                    "Der Preis darf nicht negativ sein.");
        }
        this.ownPriceInEur = ownPriceInEur;
    }

    public double getMaxWeightInKg() {
        return maxWeightInKg;
    }

    public void setMaxWeightInKg(double maxWeightInKg) {
        if (!Double.isFinite(maxWeightInKg) || maxWeightInKg <= 0) {
            throw new IllegalArgumentException(
                    "Das maximale Inhaltsgewicht muss positiv sein.");
        }
        this.maxWeightInKg = maxWeightInKg;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("Die Item-Liste darf nicht null sein.");
        }
        this.items = new ArrayList<>(items);
    }
}
