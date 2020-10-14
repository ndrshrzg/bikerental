package org.ndrshrzg.bikerental.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {

    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private float latitude;
    private float longitude;
    private boolean rented;

    public User(){}

    public User(String name, float latitude, float longitude, boolean rented) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rented = rented;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean hasRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{id=" + this.getId() + ",name=" + this.getName() + ",latitude=" + this.getLatitude() +
                ",longitude=" + this.getLongitude() + ",rented=" + this.hasRented() + "}";
    }
}
