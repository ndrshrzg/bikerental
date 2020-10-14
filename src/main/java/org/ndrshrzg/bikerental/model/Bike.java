package org.ndrshrzg.bikerental.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Bike {

    private @Id
    @GeneratedValue
    Long bikeId;
    private String frame;
    private boolean free;

    public Bike(String frame, boolean free) {
        this.frame = frame;
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
