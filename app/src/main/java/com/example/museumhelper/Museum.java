package com.example.museumhelper;

import java.io.Serializable;
import java.util.List;

public class Museum implements Serializable {
    long id;
    String name;
    Country country;
    City city;
    List<ArtMovement> artMovementList;

    public Museum(long id, String name, Country country, City city, List<ArtMovement> artMovementList) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.artMovementList = artMovementList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<ArtMovement> getArtMovementList() {
        return artMovementList;
    }

    public void setArtMovementList(List<ArtMovement> artMovementList) {
        this.artMovementList = artMovementList;
    }

    @Override
    public String toString() {
        String str =
                "id=" + id + '\n' +
                "name=" + name + '\n' +
                "country=" + country.getName() + '\n' +
                "city=" + city.getName() + '\n' +
                "art movements= ";

        StringBuffer strToDisplay = new StringBuffer();
        strToDisplay.append(str);
        for(ArtMovement am : artMovementList) {
            strToDisplay.append(am.getName());
            strToDisplay.append(", ");
        }
        return strToDisplay.substring(0,strToDisplay.length()-2);
    }
}
