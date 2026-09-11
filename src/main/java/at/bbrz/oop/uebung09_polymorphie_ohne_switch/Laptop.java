package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public record Laptop(
        String brand,
        int batteryPercentage,
        double weightInKg,
        double priceInEur) implements Item {
    @Override
    public String getDescription() {
        return "Laptop von " + brand + " mit " + batteryPercentage + " % Akku";
    }
}
