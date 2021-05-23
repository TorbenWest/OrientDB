package com.orientdb.entities;

public class Street {

    private final String name;
    private final String number;
    private final String extension;

    public Street(String name, String number, String extension) {
        this.name = name;
        this.number = number;
        this.extension = extension;
    }

    public Street(String name, String number) {
        this(name, number, "");
    }

    public Street(String name) {
        this(name, "");
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getExtension() {
        return extension;
    }
}
