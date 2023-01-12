package com.comfone.audit.test.audittest.data;


import lombok.*;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Audited
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Parent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @AuditMappedBy(mappedBy = "parent")
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    private Set<ParentChild> parentChildren = new HashSet<>();

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    public void addParentChild(ParentChild parentChild) {
        parentChild.setParent(this);
        this.parentChildren.add(parentChild);
    }

    public void removeParentChild(ParentChild parentChild) {
        parentChild.setParent(null);
        this.parentChildren.remove(parentChild);
    }
}