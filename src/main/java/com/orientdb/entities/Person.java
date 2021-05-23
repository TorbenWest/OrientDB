package com.orientdb.entities;

import java.io.Serializable;

public class Person implements Serializable {

    private final String name;
    private final Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }
}
