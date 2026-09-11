package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public record Book(String title, String author) implements Item {
    @Override
    public String getDescription() {
        return "Buch: \"" + title + "\" von " + author;
    }
}
