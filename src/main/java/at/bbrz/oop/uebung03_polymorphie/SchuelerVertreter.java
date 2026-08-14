package at.bbrz.oop.uebung03_polymorphie;

public class SchuelerVertreter extends Schueler {
    public SchuelerVertreter(String name) {
        super(name);
    }

    @Override
    public String gibTaetigkeit() {
        return super.gibTaetigkeit().replace('.',' ')
                +"und ist für seine Mitschüler da.";
    }
}
