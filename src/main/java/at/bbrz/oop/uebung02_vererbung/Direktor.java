package at.bbrz.oop.uebung02_vererbung;

public class Direktor extends Person{
    private String schulName;

    public Direktor(String name, String schulName) {
        super(name);
        this.schulName = schulName;
    }
    public void leiten() {
        System.out.println(getName()+ " leitet die Schule " + schulName);
    }
}
