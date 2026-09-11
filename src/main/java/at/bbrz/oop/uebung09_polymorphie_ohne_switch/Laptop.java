package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class Laptop implements Item {
    private final String brand;
    private final int batteryPercentage;

    public Laptop(String brand, int batteryPercentage) {
        this.brand = brand;
        this.batteryPercentage = batteryPercentage;
    }

    @Override
    public String getDescription() {
        return "Laptop von " + brand + " mit " + batteryPercentage + " % Akku";
    }
}
