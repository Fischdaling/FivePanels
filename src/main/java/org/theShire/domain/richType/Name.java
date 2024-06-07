package org.theShire.domain.richType;

import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.hasMaxLength;


public record Name(String value) {
    public Name(String value) {
        this.value = hasMaxLength(value, 30, "Name", MedicalDoctorException.class);
    }

    @Override
    public String toString() {
        return value;
    }
}
