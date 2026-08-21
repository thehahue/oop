package at.bbrz.oop.uebung05_schulverwaltung;

public class Uebung05 {
    public static void main(String[] args) {
        Schule schule = new Schule("BBRZ-Schule");
        Lehrkraft lehrkraft = new Lehrkraft(1, "Frau Novak", "Programmieren");
        Schueler mia = new Schueler(2, "Mia", "JAVA-1");
        Schueler leon = new Schueler(3, "Leon", "JAVA-1");
        Schueler leonMitGleicherID = new Schueler(3, "Leon", "JAVA-1");

        schule.personAufnehmen(lehrkraft);
        schule.personAufnehmen(mia);
        schule.personAufnehmen(leon);
        //schule.personAufnehmen(leonMitGleicherID); -> hier wird eine Exception geworfen und das Programm stürzt ab

        schule.personenAusgeben();

        Kurs javaKurs = new Kurs("Objektorientierte Programmierung", lehrkraft);
        javaKurs.anmelden(mia);
        javaKurs.anmelden(leon);
        javaKurs.ausgeben();

        Schulperson gefunden = schule.findePerson(3);
        System.out.println("Gefunden: " + gefunden.getBeschreibung());

        // TODO: Verhindere in Schule doppelte IDs.
        // TODO: Fuege Direktor als weitere Schulperson hinzu.
        // TODO: Baue eine abmelden-Methode in Kurs ein.
    }
}
