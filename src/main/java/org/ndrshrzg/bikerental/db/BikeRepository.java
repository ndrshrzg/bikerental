package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, String> {
}
