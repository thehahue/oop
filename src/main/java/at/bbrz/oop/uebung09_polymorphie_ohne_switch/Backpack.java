package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class Backpack implements Item {
    private final String color;
    private final int numberOfCompartments;

    public Backpack(String color, int numberOfCompartments) {
        this.color = color;
        this.numberOfCompartments = numberOfCompartments;
    }

    @Override
    public String getDescription() {
        return "Rucksack in " + color + " mit " + numberOfCompartments
                + " Faechern";
    }
}
