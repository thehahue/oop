package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class Book implements Item {
    private final String title;
    private final String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String getDescription() {
        return "Buch: \"" + title + "\" von " + author;
    }
}
