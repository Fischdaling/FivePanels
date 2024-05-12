package org.theShire.domain.MedicalDoctor;

import org.theShire.domain.BaseEntity;

import java.time.Instant;
import java.time.Year;

public class User extends BaseEntity {

    public User() {
        super(Instant.now());
    }
}
