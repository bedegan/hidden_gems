package com.cs188group6.hiddengems_dsm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Gem extends RealmObject {
    @PrimaryKey
    private String id;
    private String gemName, address, hours, neighborhood, tags, description;
    private double latitude, longitude;
    private int points;
    private byte[] image;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGemName() {
        return gemName;
    }

    public void setGemName(String gemName) {
        this.gemName = gemName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoints() {
        return Integer.toString(points);
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
