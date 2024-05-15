package org.theShire.domain.richType;

import java.util.List;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.hasMaxLength;


public record EducationalTitle(String value) {
    public EducationalTitle(String value) {
        this.value = hasMaxLength(value,30,"educationalTitle",exTypeUser);
    }
}
