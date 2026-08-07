package at.bbrz.oop.uebung04_interfaces;

public interface Benotbar {
    double getNote();

    default boolean istBestanden() {
        return getNote() <= 4.0;
    }
}
