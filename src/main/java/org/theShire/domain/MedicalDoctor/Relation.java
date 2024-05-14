package org.theShire.domain.MedicalDoctor;

import org.theShire.domain.BaseEntity;

import java.time.Instant;

public class Relation extends BaseEntity {

    private RelationType type;

    public Relation() {
        super(Instant.now());
    }

    public Relation(Instant createdAt, RelationType type) {
        super(createdAt);
        this.type = type;
    }

    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    enum RelationType{
        OUTGOING,
        INCOMING,
        ESTABLISHED,
    }
}
