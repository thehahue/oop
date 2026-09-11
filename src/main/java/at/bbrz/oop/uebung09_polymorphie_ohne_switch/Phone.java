package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public record Phone(
        String brand,
        String model,
        int batteryPercentage,
        double weightInKg,
        double priceInEur) implements Item {
    @Override
    public String getDescription() {
        return "Smartphone " + brand + " " + model + " mit "
                + batteryPercentage + " % Akku";
    }
}
