package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public record Backpack(
        String color,
        int numberOfCompartments,
        double weightInKg,
        double priceInEur) implements Item {
    @Override
    public String getDescription() {
        return "Rucksack in " + color + " mit " + numberOfCompartments
                + " Faechern";
    }
}
