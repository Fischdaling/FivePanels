package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MediaException;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.Email;
import org.theShire.domain.richType.Password;
import org.theShire.foundation.DomainAssertion;

import javax.print.attribute.standard.Media;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.theShire.foundation.DomainAssertion.*;

public class User extends BaseEntity {
    // exeption Type if something throws
    private static final Class<MedicalDoctorException> exType = MedicalDoctorException.class;

    //The Email must contain @ and . cannot be empty and has a max length
    private Email email;
    //The Password must be up to certain standards (not null, not Empty, min length,...)
    private Password password;
    // The UserProfile contains ertain information about the user (Easy to display)
    private UserProfile profile;
    // The Score of the User gets increased when sucessfully publishing cases and/or voting on cases and/or leaving comments
    private int score;
    //The Contact list of a user (What Friendship he has)
    private Set<UserRelationShip> contacts;
    //The Chats from a User (single and Group chats)
    private Set<Chat> chats;
    // all the cases owned and lead by the User
    private Set<Case> ownedCases;
    // all the cases the user is a member of
    private Set<Case> memberOfCase;


    public User() {
        super(Instant.now());
    }

    public User(Instant createdAt,Password password,Email email, UserProfile profile, Set<UserRelationShip> contacts) {
        super(createdAt);
        this.password = password;
        this.email = email;
        this.profile = profile;
        this.contacts = contacts;
    }


    // SETTER & GETTER -----------------------------------------------------------
    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        //TODO ASSERTION
        this.profile = profile;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        //TODO ASSERTION

        this.score = score;
    }

    public Set<UserRelationShip> getContacts() {
        return contacts;
    }



    public Set<Chat> getChats() {
        return chats;
    }


    public Set<Case> getOwnedCases() {
        return ownedCases;
    }


    public Set<Case> isMemberOfCases() {
        return memberOfCase;
    }

    // Methods ------------------------------------------------------------

    public void addChat(Chat chat){
        isNotInCollection(chat, chats,"Chat already in Set", exType);
        this.chats.add(chat);
    }

    public void addContact(UserRelationShip contact){
        isNotInCollection(contact, contacts,"Contact already in Set", exType);
        this.contacts.add(contact);
    }

    public void createCase(String title, List<Content> content, UUID...members){

        this.ownedCases.add(new Case(this.getEntityId(),title, content, members));
    }
}
