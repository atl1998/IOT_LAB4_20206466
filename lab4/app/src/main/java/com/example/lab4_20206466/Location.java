package com.example.lab4_20206466;
public class Location {
    private String idLocation;
    private String name;
    private String region;
    private String country;


    public Location() {
        this.idLocation = idLocation;
        this.name = name;
        this.region = region;
        this.country = country;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
