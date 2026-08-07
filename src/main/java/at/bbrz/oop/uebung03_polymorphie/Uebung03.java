package at.bbrz.oop.uebung03_polymorphie;

public class Uebung03 {
    public static void main(String[] args) {
        Schulmitglied[] mitglieder = {
                new Schueler("Lina"),
                new Lehrkraft("Herr Yilmaz"),
                new Hausmeister("Herr Kurz")
        };

        // Polymorphie -> Late Binding
        //                Die Methode wird erst zur Programmlaufzeit bestimmt, die aufgerufen wird (Schuler, Lehrkraft, ...)
        for (Schulmitglied mitglied : mitglieder) {
            System.out.println(mitglied.gibTaetigkeit());
        }

        // Polymorphie -> Early Binding
        //                Die Methode wird zur Compilezeit bestimme, die aufgerufen wird (Schuler.gibTaetigkeit())
        Schueler heidi = new Schueler("Heidi");
        System.out.println(heidi.gibTaetigkeit());
        // TODO: Fuege eine Direktor-Klasse hinzu. Die Schleife darf unveraendert bleiben.
    }
}
