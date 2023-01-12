package com.comfone.audit.test.audittest.data;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Audited
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"parentChildId"})
public class ParentChild implements Serializable {

    @EmbeddedId
    private final ParentChildId parentChildId = new ParentChildId();

    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("parentId")
    @JoinColumn(insertable = false, updatable = false)
    private Parent parent;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("childId")
    @JoinColumn(insertable = false, updatable = false)
    private Child child;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;
}