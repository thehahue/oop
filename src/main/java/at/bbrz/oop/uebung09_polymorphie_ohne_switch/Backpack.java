package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class Backpack implements Item {
    private String color;
    private int numberOfCompartments;

    public Backpack() {
    }

    public Backpack(String color, int numberOfCompartments) {
        this.color = color;
        this.numberOfCompartments = numberOfCompartments;
    }

    public String getColor() {
        return color;
    }

    public int getNumberOfCompartments() {
        return numberOfCompartments;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNumberOfCompartments(int numberOfCompartments) {
        this.numberOfCompartments = numberOfCompartments;
    }

    @Override
    public String getDescription() {
        return "Rucksack in " + color + " mit " + numberOfCompartments
                + " Faechern";
    }
}
