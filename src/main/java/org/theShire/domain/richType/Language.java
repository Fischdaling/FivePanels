package org.theShire.domain.richType;

import org.theShire.domain.exception.MedicalDoctorException;

import static org.theShire.foundation.DomainAssertion.*;

public record Language(String value) {
    public Language(String value) {
        this.value = hasMaxLength(value,50,"Language", MedicalDoctorException.class);
    }
}
