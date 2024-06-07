package org.theShire.domain.richType;

import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.isValidEmail;

public record Email(String value) {
    public Email(String value) {

        this.value = isValidEmail(value, "Email", MedicalDoctorException.class);
    }

    @Override
    public String toString() {
        return value;
    }
}
