package org.theShire.domain.richType;

import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.hasMaxLength;

public record Location(String value) {
    public Location(String value) {
        this.value = hasMaxLength(value, 50, "Location", MedicalDoctorException.class);
    }

    @Override
    public String toString() {
        return value;
    }
}
