package org.theShire.domain.richType;

import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.*;

public record Email(String value) {
    public Email(String value) {
        hasMaxLength(value, 50, "email", MedicalDoctorException.class);
        this.value = containsSymbols(value,"Email", MedicalDoctorException.class,'@','.');
    }

    @Override
    public String toString() {
        return value;
    }
}
