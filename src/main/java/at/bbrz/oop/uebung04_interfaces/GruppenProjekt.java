package at.bbrz.oop.uebung04_interfaces;

public class GruppenProjekt extends Schulleistung {
    private String projektTitel;

    public GruppenProjekt(String schuelerName, double note, String projektTitel) {
        super(schuelerName, note);
        this.projektTitel = projektTitel;
    }

    @Override
    public String getArt() {
        return "Gruppen Projekt - " + projektTitel;
    }

    @Override
    public boolean geheim() {
        return false;
    }
}
