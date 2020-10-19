package org.ndrshrzg.bikerental.db;

import org.ndrshrzg.bikerental.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Session s set s.sessionEnd = :sessionEnd where s.sessionId = :sessionId")
    void setSessionEnd(@Param("sessionId") String sessionId, @Param("sessionEnd") Long sessionEnd);

}
