package at.bbrz.oop.uebung01_datenkapselung;

public class Uebung01 {
    public static void main(String[] args) {
        Schueler schueler = new Schueler("Mia", 15);
        schueler.vorstellen();
        schueler.setAlter(16);
        System.out.println("Neues Alter: " + schueler.getAlter());

        // Probiere aus: Warum ist schueler.alter = -3; nicht erlaubt?
    }
}
