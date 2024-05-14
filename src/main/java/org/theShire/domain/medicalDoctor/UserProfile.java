package org.theShire.domain.medicalDoctor;

import org.theShire.domain.media.Media;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;

import java.util.Set;

public class UserProfile {

    private Name firstName;
    private Name lastName;
    private Email email;
    private Password password;
    private Set<EducationalTitle> educationalTitle;
    private Media profilePicture;
    private Location location;
    private Language language;
    private Set<Knowledges> specialization;
    private Set<Knowledges> experience;

    public UserProfile(Language language, Location location, Media profilePicture, Password password, Name firstName, Name lastName, Email email, Set<EducationalTitle> educationalTitle) {
        this.language = language;
        this.location = location;
        this.profilePicture = profilePicture;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.educationalTitle = educationalTitle;
    }

    public Name getFirstName() {
        return firstName;
    }

    public void setFirstName(Name firstName) {
        this.firstName = firstName;
    }

    public Name getLastName() {
        return lastName;
    }

    public void setLastName(Name lastName) {
        this.lastName = lastName;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Set<EducationalTitle> getEducationalTitle() {
        return educationalTitle;
    }

    public void setEducationalTitle(Set<EducationalTitle> educationalTitle) {
        this.educationalTitle = educationalTitle;
    }

    public Media getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Media profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Knowledges> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Set<Knowledges> specialization) {
        this.specialization = specialization;
    }

    public Set<Knowledges> getExperience() {
        return experience;
    }

    public void setExperience(Set<Knowledges> experience) {
        this.experience = experience;
    }
}
