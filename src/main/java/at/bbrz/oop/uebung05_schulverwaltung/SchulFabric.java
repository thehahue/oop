package at.bbrz.oop.uebung05_schulverwaltung;

public class SchulFabric {
    public static Schule createSchule(String nameOfSchule, String nameOfDirektor) {
        Schule schule = new Schule(nameOfSchule);
        Direktor direktor = new Direktor(1, nameOfDirektor);
        schule.direktorAufnehmen(direktor);

        return schule;
    }
}
