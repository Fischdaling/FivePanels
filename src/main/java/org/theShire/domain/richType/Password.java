package org.theShire.domain.richType;

import com.nulabinc.zxcvbn.Zxcvbn;
import org.theShire.domain.exception.MedicalDoctorException;

import java.util.Arrays;

import static org.theShire.foundation.DomainAssertion.isZxcvbn3Confirm;

public record Password(String value) {
    public Password(String value) {
        this.value = Arrays.toString(isZxcvbn3Confirm(value, "Password", MedicalDoctorException.class));
    }
}
