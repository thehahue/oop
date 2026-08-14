package at.bbrz.oop.uebung03_polymorphie;

public class PersonenNummernVerwaltung {
    private static int currentPersonenNummer=0;

    public static String nextPersonenNummer() {
        currentPersonenNummer++;
        return ""+currentPersonenNummer;
    }
}
