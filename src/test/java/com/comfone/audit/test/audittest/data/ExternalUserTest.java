package com.comfone.audit.test.audittest.data;

import com.comfone.audit.test.audittest.repository.MandateRepository;
import com.comfone.audit.test.audittest.repository.ParentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.history.Revisions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExternalUserTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private MandateRepository mandateRepository;

    @Test
    @Order(1)
    void createParent() {
        Parent parent = Parent.builder()
                .name("Parent")
                .build();

        parent = this.parentRepository.saveAndFlush(parent);

        assertNotNull(parent);
        assertNotNull(parent.getId());
        assertEquals(0, parent.getVersion());

        Revisions<Long, Parent> revisions = this.parentRepository.findRevisions(parent.getId());
        assertNotNull(revisions);
        assertNotNull(revisions.getContent());
        assertEquals(1, revisions.getContent().size());
    }

    @Test
    @Order(2)
    void addChild1() {
        Parent parent = this.parentRepository.getParentById(1L).orElseThrow();

        Child child1 = Child.builder()
                .name("Child 1")
                .build();

        child1 = this.mandateRepository.saveAndFlush(child1);

        parent.addParentChild(
                ParentChild.builder()
                        .name("ParentChild 1")
                        .child(child1)
                        .build()
        );

        parent = this.parentRepository.saveAndFlush(parent);

        assertNotNull(parent);
        assertNotNull(parent.getId());
        assertEquals(1, parent.getVersion());
        assertEquals(1, parent.getParentChildren().size());

        Revisions<Long, Parent> revisions = this.parentRepository.findRevisions(parent.getId());
        assertNotNull(revisions);
        assertNotNull(revisions.getContent());
        assertEquals(2, revisions.getContent().size());
    }

    @Test
    @Order(3)
    void addUserMandate2() {
        Parent user = this.parentRepository.getParentById(1L).orElseThrow();

        Child child2 = Child.builder()
                .name("Child 2")
                .build();

        child2 = this.mandateRepository.saveAndFlush(child2);

        user.addParentChild(
                ParentChild.builder()
                        .name("ParentChild 2")
                        .child(child2)
                        .build()
        );

        user = this.parentRepository.saveAndFlush(user);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(2, user.getVersion());
        assertEquals(2, user.getParentChildren().size());

        Revisions<Long, Parent> revisions = this.parentRepository.findRevisions(user.getId());
        assertNotNull(revisions);
        assertNotNull(revisions.getContent());
        assertEquals(3, revisions.getContent().size());
    }

    @Test
    @Order(4)
    void removeChild2() {
        Parent user = this.parentRepository.getParentById(1L).orElseThrow();

        user.getParentChildren().stream().filter(parentChild -> parentChild.getName().equals("Child 2")).forEach(user::removeParentChild);

        user = this.parentRepository.saveAndFlush(user);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(3, user.getVersion());
        assertEquals(1, user.getParentChildren().size());

        Revisions<Long, Parent> revisions = this.parentRepository.findRevisions(user.getId());
        assertNotNull(revisions);
        assertNotNull(revisions.getContent());
        assertEquals(4, revisions.getContent().size());
    }
}