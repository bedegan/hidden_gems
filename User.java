package com.cs188group6.hiddengems_dsm;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private String id;//this is the primary key
    private String email, name, password; // changed username to email, added name attribute
    private int points, level; // added level attribute
    private RealmList<Gem> savedSpots;
    private Gem collectedGem;
    private byte[] profilePic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public RealmList<Gem> getSavedSpots() {
        return savedSpots;
    }

    public void setSavedSpots(RealmList<Gem> savedSpots) {
        this.savedSpots = savedSpots;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public Gem getCollectedGem() {
        return collectedGem;
    }

    public void setCollectedGem(Gem collectedGem) {
        this.collectedGem = collectedGem;
    }
}
