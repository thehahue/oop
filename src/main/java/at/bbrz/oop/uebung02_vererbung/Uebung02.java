package at.bbrz.oop.uebung02_vererbung;

public class Uebung02 {
    public static void main(String[] args) {
        Schueler schueler = new Schueler("Noah", "2A");
        Lehrkraft lehrkraft = new Lehrkraft("Frau Berger", "Mathematik");

        schueler.vorstellen();
        schueler.lernen();
        lehrkraft.vorstellen();
        lehrkraft.unterrichten();

        // TODO: Erstelle eine weitere Unterklasse Direktor mit der Methode leiten().
    }
}
