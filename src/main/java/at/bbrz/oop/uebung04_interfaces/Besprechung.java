package at.bbrz.oop.uebung04_interfaces;

public class Besprechung implements Geheim{
    @Override
    public boolean geheim() {
        return false;
    }
}
