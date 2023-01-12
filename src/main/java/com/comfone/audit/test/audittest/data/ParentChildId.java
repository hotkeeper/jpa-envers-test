package com.comfone.audit.test.audittest.data;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"parentId", "childId"})
public class ParentChildId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Long parentId;

    protected Long childId;
}
