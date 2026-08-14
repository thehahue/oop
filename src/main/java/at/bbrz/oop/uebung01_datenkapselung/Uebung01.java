package at.bbrz.oop.uebung01_datenkapselung;

public class Uebung01 {
    public static void main(String[] args) {

        try {
            Schueler mia = new Schueler("Mia", 15);
            mia.vorstellen();
            //mia.setAlter(16);
            System.out.println("Alter: " + mia.getAlter());

            Schueler franz = new Schueler("Franz", -1);
            franz.vorstellen();
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
        // Probiere aus: Warum ist mia.alter = -3; nicht erlaubt?
    }
}
