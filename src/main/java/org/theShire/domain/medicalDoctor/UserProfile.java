package org.theShire.domain.medicalDoctor;

import org.theShire.domain.media.Media;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.isInCollection;
import static org.theShire.foundation.DomainAssertion.isNotNull;

public class UserProfile {

    private Name firstName;
    private Name lastName;
    private Set<EducationalTitle> educationalTitles;
    private Media profilePicture;
    private Location location;
    private Language language;
    private Set<Knowledges> specialization;
    private Set<Knowledges> experience;

    public UserProfile(Language language, Location location, Media profilePicture, Name firstName, Name lastName, EducationalTitle...educationalTitle) {
        this.language = language;
        this.location = location;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        educationalTitles = new LinkedHashSet<>();
        this.addEducationalTitle(educationalTitle[0]);
    }

    public Name getFirstName() {
        return firstName;
    }

    public void setFirstName(Name firstName) {
        this.firstName = isNotNull(firstName, "firstName", exTypeUser);
    }

    public Name getLastName() {
        return lastName;
    }

    public void setLastName(Name lastName) {
        this.lastName = isNotNull(lastName, "lastName", exTypeUser);
    }


    public Media getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Media profilePicture) {
        this.profilePicture = isNotNull(profilePicture, "profilePicture", exTypeUser);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = isNotNull(location, "location", exTypeUser);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = isNotNull(language, "language", exTypeUser);
    }

    public Set<Knowledges> getSpecialization() {
        return specialization;
    }

    public Set<Knowledges> getExperience() {
        return experience;
    }


    public void addEducationalTitle(EducationalTitle educationalTitle) {
        this.educationalTitles.add(isInCollection(educationalTitle, educationalTitles,"educationalTitle", exTypeUser));
    }
    public void addEducationalTitles(EducationalTitle...educationalTitle) {
        for (EducationalTitle title : educationalTitle) {
            addEducationalTitle(title);
        }
    }


    public void addSpecialization(Knowledges specialization) {
        this.specialization.add(isNotNull(specialization, "specialization", exTypeUser));
    }

    public void setExperience(Knowledges experience) {
        this.experience.add(isNotNull(experience, "experience", exTypeUser));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("firstName=").append(firstName).append(System.lineSeparator());
        sb.append("lastName=").append(lastName).append(System.lineSeparator());
        sb.append("educationalTitles=").append(educationalTitles).append(System.lineSeparator());
        sb.append("profilePicture=").append(profilePicture).append(System.lineSeparator());
        sb.append("location=").append(location).append(System.lineSeparator());
        sb.append("language=").append(language).append(System.lineSeparator());
        sb.append("specialization=").append(specialization).append(System.lineSeparator());
        sb.append("experience=").append(experience).append(System.lineSeparator());
        return sb.toString();
    }
}
