package org.theShire.domain.MedicalDoctor;

import org.theShire.domain.Media.Media;
import org.theShire.domain.RichType.*;

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


}
