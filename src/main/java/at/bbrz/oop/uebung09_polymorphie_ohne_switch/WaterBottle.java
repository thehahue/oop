package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class WaterBottle implements Item {
    private String material;
    private int capacityInMilliliters;

    public WaterBottle() {
    }

    public WaterBottle(String material, int capacityInMilliliters) {
        this.material = material;
        this.capacityInMilliliters = capacityInMilliliters;
    }

    public String getMaterial() {
        return material;
    }

    public int getCapacityInMilliliters() {
        return capacityInMilliliters;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setCapacityInMilliliters(int capacityInMilliliters) {
        this.capacityInMilliliters = capacityInMilliliters;
    }

    @Override
    public String getDescription() {
        return "Wasserflasche aus " + material + " mit "
                + capacityInMilliliters + " ml Fassungsvermoegen";
    }
}
