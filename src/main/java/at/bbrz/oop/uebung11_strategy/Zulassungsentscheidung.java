package at.bbrz.oop.uebung11_strategy;

/**
 * Ergebnis einer fachlichen Zulassungspruefung.
 */
public record Zulassungsentscheidung(boolean erlaubt, String begruendung) {
    public Zulassungsentscheidung {
        if (begruendung == null || begruendung.isBlank()) {
            throw new IllegalArgumentException("Die Begruendung darf nicht leer sein.");
        }
    }

    public static Zulassungsentscheidung erlaubt(String begruendung) {
        return new Zulassungsentscheidung(true, begruendung);
    }

    public static Zulassungsentscheidung abgelehnt(String begruendung) {
        return new Zulassungsentscheidung(false, begruendung);
    }
}
