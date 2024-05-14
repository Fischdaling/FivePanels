package org.theShire.domain.richType;
import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.*;


public record Name(String value) {
    public Name(String value) {
        hasMaxLength(value,30,"Name", MedicalDoctorException.class);
        this.value = value;
    }
}
