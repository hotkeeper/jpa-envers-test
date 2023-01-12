package com.comfone.audit.test.audittest.repository;

import com.comfone.audit.test.audittest.data.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface MandateRepository extends JpaRepository<Child, Long>, RevisionRepository<Child, Long, Long> {

}