package com.neuer.healthyphone;

public class Boutique {
    String name;
    String Wilaya;
    String City;
    String UID;
    String Key;
    String Phone;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Boutique(String name, String wilaya, String city, String UID, String key) {
        this.name = name;
        Wilaya = wilaya;
        City = city;
        this.UID = UID;
        Key = key;
    }

    public Boutique() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public void setWilaya(String wilaya) {
        Wilaya = wilaya;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
