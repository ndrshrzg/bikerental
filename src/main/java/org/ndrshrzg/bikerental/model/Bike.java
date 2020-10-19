package org.ndrshrzg.bikerental.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Bikes")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bikeId;

    private String frame;
    private float latitude;
    private float longitude;
    private boolean free;

    public Bike(String frame, float latitude, float longitude, boolean free) {
        this.frame = frame;
        this.latitude = latitude;
        this.longitude = longitude;
        this.free = free;
    }

    public Bike() {
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return bikeId.equals(bike.bikeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bikeId);
    }

    @Override
    public String toString() {
        return "{bikeId:" + this.getBikeId() + ",frame:" + this.getFrame() + ",isFree:" + this.isFree() + "}";
    }
}
