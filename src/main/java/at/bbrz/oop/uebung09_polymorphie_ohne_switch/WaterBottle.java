package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public record WaterBottle(
        String material, int capacityInMilliliters) implements Item {
    @Override
    public String getDescription() {
        return "Wasserflasche aus " + material + " mit "
                + capacityInMilliliters + " ml Fassungsvermoegen";
    }
}
