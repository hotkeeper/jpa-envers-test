package com.comfone.audit.test.audittest.repository;

import com.comfone.audit.test.audittest.data.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long>, RevisionRepository<Parent, Long, Long> {

    @Query("""
            SELECT p FROM Parent p
            LEFT JOIN FETCH p.parentChildren pc
            LEFT JOIN FETCH pc.child c
            WHERE p.id = :id
            """)
    Optional<Parent> getParentById(Long id);
}