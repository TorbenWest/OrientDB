package com.orientdb.entities;

public class Address {

    private final City city;
    private final Street street;
    private final String country;

    public Address(City city, Street street, String country) {
        this.city = city;
        this.street = street;
        this.country = country;
    }

    public Address(String city, String code, String street, String country) {
        this(new City(city, code), new Street(street), country);
    }

    public City getCity() {
        return city;
    }

    public Street getStreet() {
        return street;
    }

    public String getCountry() {
        return country;
    }
}
