package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MedicalDoctorException;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.isNotNull;

public class Relation extends BaseEntity {
    // The type of a Relation (OUTGOING,INCOMING,ESTABLISHED)
    private RelationType type;
    private Set<UUID> userWithRelation;


    public Relation() {
        super(Instant.now());
    }

    public Relation(RelationType type, UUID userWithRelation) {
        super(Instant.now());
        this.userWithRelation = new HashSet<>();
        this.type = type;
        this.userWithRelation.add(userWithRelation);
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
