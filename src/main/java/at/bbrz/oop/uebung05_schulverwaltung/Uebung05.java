package at.bbrz.oop.uebung05_schulverwaltung;

public class Uebung05 {
    public static void main(String[] args) {
        Schule schule = SchulFabric.createSchule("BBRZ Schule","Franz Hofer");
        Lehrkraft lehrkraft = new Lehrkraft(9, "Frau Novak", "Programmieren");
        Schueler mia = new Schueler(2, "Mia", "JAVA-1");
        Schueler leon = new Schueler(3, "Leon", "JAVA-1");
        Schueler mitGleicherID = new Schueler(3, "Franz", "JAVA-1");
        Schueler mitGleichemNamen = new Schueler(4, "Mia", "JAVA-1");

        Schule schule2 = SchulFabric.createSchule("BBRZ Schule 2", "Mia Biber");
        schule2.personenAusgeben();

        schule.personAufnehmen(lehrkraft);
        schule.personAufnehmen(mia);
        schule.personAufnehmen(leon);
        //schule.personAufnehmen(mitGleicherID); -> hier wird eine Exception geworfen und das Programm stürzt ab
        //schule.personAufnehmen(mitGleichemNamen);
        //schule.personAufnehmen(null);

        schule.personenAusgeben();

        Kurs javaKurs = new Kurs("Objektorientierte Programmierung", lehrkraft);
        javaKurs.anmelden(mia);
        javaKurs.anmelden(leon);
        javaKurs.ausgeben();

        javaKurs.abmelden(2);
        javaKurs.ausgeben();

        Schulperson gefunden = schule.findePerson(3);
        System.out.println("Gefunden: " + gefunden.getBeschreibung());

        // TODO: Verhindere in Schule doppelte IDs.
        // TODO: Fuege Direktor als weitere Schulperson hinzu.
        // TODO: Baue eine abmelden-Methode in Kurs ein.
    }
}
