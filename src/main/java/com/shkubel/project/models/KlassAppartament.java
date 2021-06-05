package com.shkubel.project.models;

public enum KlassAppartament {
    HIGH ("High"),
    MEDIUM ("Medium"),
    LOW ("Low"),
    UNKNOWN ("Unknown");

    private final String name;

    KlassAppartament(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
