package at.bbrz.oop.uebung04_interfaces;

public abstract class Schulleistung implements Benotbar, Geheim {
    private final String schuelerName;
    private double note;

    protected Schulleistung(String schuelerName, double note) {
        if (schuelerName == null || schuelerName.isBlank()) {
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        this.schuelerName = schuelerName;
        setNote(note);
    }

    public String getSchuelerName() {
        return schuelerName;
    }

    @Override
    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        if (note < 1.0 || note > 5.0) {
            throw new IllegalArgumentException("Die Note muss zwischen 1 und 5 liegen.");
        }
        this.note = note;
    }

    public abstract String getArt();
}
