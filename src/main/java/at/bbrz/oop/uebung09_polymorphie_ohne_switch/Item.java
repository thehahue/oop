package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Gemeinsamer Vertrag fuer alle Gegenstaende.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "klasse")
public interface Item {
    double weightInKg();

    double priceInEur();

    @JsonIgnore
    String getDescription();
}
