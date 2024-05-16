package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MedicalDoctorException;

import java.time.Instant;
import java.util.UUID;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.isNotNull;

public class Relation extends BaseEntity {
    // The type of a Relation (OUTGOING,INCOMING,ESTABLISHED)
    private RelationType type;
    // The User Whom the type is connected to
    private UUID relatedUserId;

    public Relation() {
        super(Instant.now());
    }

    public Relation(RelationType type, UUID relatedUserId) {
        super(Instant.now());
        this.type = type;
        this.relatedUserId = relatedUserId;
    }

    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = isNotNull(type, "Relation type can't be null", exTypeUser);
    }

    enum RelationType{
        OUTGOING,
        INCOMING,
        ESTABLISHED,
    }
}
