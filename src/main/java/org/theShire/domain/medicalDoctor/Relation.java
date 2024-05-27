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
    //TODO ADD describtions to the atributes
    private User user1;
    private User user2;
    // The type of a Relation (OUTGOING,INCOMING,ESTABLISHED)
    private RelationType type;


    public Relation(User user1, User user2, RelationType relationType) {
        setUser(user1);
        setUser(user2);
        setType(relationType);
    }

    private void setUser(User user) {
        this.user1 = isNotNull(user, "user",exTypeUser);
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = isNotNull(type, "Relation type can't be null", exTypeUser);
    }


    public enum RelationType{
        OUTGOING,
        INCOMING,
        ESTABLISHED,
    }
}
