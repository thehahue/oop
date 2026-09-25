package at.bbrz.oop.uebung11_strategy;

/**
 * Rueckgabewert des Anwendungsfalls "Schueler zu Kurs anmelden".
 */
public record Anmeldeergebnis(boolean erfolgreich, String nachricht) {
    public Anmeldeergebnis {
        if (nachricht == null || nachricht.isBlank()) {
            throw new IllegalArgumentException("Die Nachricht darf nicht leer sein.");
        }
    }

    public static Anmeldeergebnis erfolgreich(String nachricht) {
        return new Anmeldeergebnis(true, nachricht);
    }

    public static Anmeldeergebnis abgelehnt(String nachricht) {
        return new Anmeldeergebnis(false, nachricht);
    }
}
