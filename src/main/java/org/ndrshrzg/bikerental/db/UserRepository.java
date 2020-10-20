package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Transactional
    @Query("select u.rented from User u where u.id = :userId")
    boolean getRentedById(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.rented = :hasRented where u.id = :userId")
    void setUserHasRented(@Param("userId") Long userId, @Param("hasRented") boolean hasRented);

}
