package at.bbrz.oop.uebung04_interfaces;

public class GeheimBesprechung implements Geheim {
    private String thema;

    public GeheimBesprechung(String thema) {
        this.thema = thema;
    }

    @Override
    public boolean geheim() {
        return true;
    }
}
