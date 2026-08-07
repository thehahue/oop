package at.bbrz.oop.uebung03_polymorphie;

public class Uebung03 {
    public static void main(String[] args) {
        Schulmitglied[] mitglieder = {
                new Schueler("Lina"),
                new Lehrkraft("Herr Yilmaz"),
                new Hausmeister("Herr Kurz")
        };

        for (Schulmitglied mitglied : mitglieder) {
            mitglied.beschreiben();
        }

        // TODO: Fuege eine Direktor-Klasse hinzu. Die Schleife darf unveraendert bleiben.
    }
}
