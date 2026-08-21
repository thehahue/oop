package at.bbrz.oop.uebung04_interfaces;

import java.util.List;

public class Uebung04 {
    public static void main(String[] args) {
        List<Schulleistung> leistungen = List.of(
                new Klassenarbeit("Sara", 2.0, "Deutsch"),
                new Referat("Elias", 4.5, "Vulkane"),
                new Klassenarbeit("Amir", 1.5, "Mathematik"),
                new GruppenProjekt("Fritzi, Karli", 2.0, "Programmieren")
        );

        for (Schulleistung leistung : leistungen) {
            String ergebnis = leistung.istBestanden() ? "bestanden" : "nicht bestanden";

            if (!leistung.geheim()) {
                System.out.printf("%s: %s, Note %.1f (%s)%n",
                        leistung.getSchuelerName(), leistung.getArt(), leistung.getNote(), ergebnis);
            } else {
                System.out.printf("%s: %s, <Geheim>%n",
                        leistung.getSchuelerName(), leistung.getArt());
            }
        }

        List<Geheim> listeGeheim = List.of(
                new Referat("Mama", 4.5, "Vulkane"),
                new Klassenarbeit("Papa", 1.5, "Mathematik"),
                new GruppenProjekt("Opa", 2.0, "Programmieren"),
                new Besprechung(),
                new GeheimBesprechung("Geheimes Thema")
        );

        for (Geheim geheim : listeGeheim) {
            System.out.println("Geheim: " + geheim.geheim());
        }

        // TODO: Implementiere die Unterklasse Gruppenprojekt mit einem Projekttitel.
        // TODO: Berechne den Notendurchschnitt aller Leistungen.
    }
}
