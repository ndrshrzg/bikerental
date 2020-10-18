package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByUserId(Long userId);

}
