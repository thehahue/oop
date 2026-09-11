package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class Laptop implements Item {
    private String brand;
    private int batteryPercentage;

    public Laptop() {
    }

    public Laptop(String brand, int batteryPercentage) {
        this.brand = brand;
        this.batteryPercentage = batteryPercentage;
    }

    public String getBrand() {
        return brand;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    @Override
    public String getDescription() {
        return "Laptop von " + brand + " mit " + batteryPercentage + " % Akku";
    }
}
