package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface BikeRepository extends JpaRepository<Bike, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Bike b set b.free = :isFree where b.bikeId = :bikeId")
    void setBikeIsFree(@Param("bikeId") Long bikeId, @Param("isFree") boolean isFree);

}
