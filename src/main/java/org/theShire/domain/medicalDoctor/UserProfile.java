package org.theShire.domain.medicalDoctor;

import org.theShire.domain.media.Media;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;

import java.util.Set;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.isNotNull;

public class UserProfile {

    private Name firstName;
    private Name lastName;
    private Set<EducationalTitle> educationalTitle;
    private Media profilePicture;
    private Location location;
    private Language language;
    private Set<Knowledges> specialization;
    private Set<Knowledges> experience;

    public UserProfile(Language language, Location location, Media profilePicture, Name firstName, Name lastName, Set<EducationalTitle> educationalTitle) {
        this.language = language;
        this.location = location;
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.educationalTitle = educationalTitle;
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

    public Set<EducationalTitle> getEducationalTitle() {
        return educationalTitle;
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
        this.educationalTitle.add(isNotNull(educationalTitle, "educationalTitle", exTypeUser));
    }

    public void addSpecialization(Knowledges specialization) {
        this.specialization.add(isNotNull(specialization, "specialization", exTypeUser));
    }

    public void setExperience(Knowledges experience) {
        this.experience.add(isNotNull(experience, "experience", exTypeUser));
    }
}
