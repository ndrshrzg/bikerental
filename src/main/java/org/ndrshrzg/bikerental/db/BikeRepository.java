package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
