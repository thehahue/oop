package at.bbrz.oop.uebung03_polymorphie;

public class Uebung03 {
    public static void main(String[] args) {
        Schulmitglied[] mitglieder = {
                new Schueler("Lina"),
                new Lehrkraft("Herr Yilmaz"),
                new Hausmeister("Herr Kurz"),
                new Direktor("Frau Lutz")
        };

        // Polymorphie -> Late Binding
        //                Die Methode wird erst zur Programmlaufzeit bestimmt, die aufgerufen wird (Schuler, Lehrkraft, ...)
        for (Schulmitglied mitglied : mitglieder) {
            mitglied.beschreiben();
        }

        // Polymorphie -> Early Binding
        //                Die Methode wird zur Compilezeit bestimmt, die aufgerufen wird (Schuler.gibTaetigkeit())
        Schueler heidi = new Schueler("Heidi");
        heidi.beschreiben();
        // TODO: Fuege eine Direktor-Klasse hinzu. Die Schleife darf unveraendert bleiben.
    }
}
