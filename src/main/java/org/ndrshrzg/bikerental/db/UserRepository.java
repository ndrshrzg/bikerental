package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String userName);

}
