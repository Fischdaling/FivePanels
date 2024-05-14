package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;

import java.time.Instant;

import static org.theShire.foundation.DomainAssertion.isNotNull;

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
        isNotNull(this.type, "Relation type can't be null");
        this.type = type;
    }

    enum RelationType{
        OUTGOING,
        INCOMING,
        ESTABLISHED,
    }
}
