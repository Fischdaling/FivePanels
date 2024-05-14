package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MediaException;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.foundation.DomainAssertion;

import javax.print.attribute.standard.Media;
import java.time.Instant;
import java.util.Set;
import static org.theShire.foundation.DomainAssertion.*;

public class User extends BaseEntity {

    private UserProfile profile;
    private int score;
    private Set<UserRelationShip> contacts;
    private Set<Chat> chats;
    private Set<Case> ownedCases;
    private Set<Case> memberOfCase;
    private MedicalDoctorException e;


    public User() {
        super(Instant.now());
    }

    public User(Instant createdAt, UserProfile profile, Set<UserRelationShip> contacts) {
        super(createdAt);
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
        isNotInCollection(chat, chats,"Chat already in Set", e);
        this.chats.add(chat);
    }

    public void addContact(UserRelationShip contact){
        isNotInCollection(contact, contacts,"Contact already in Set", e);
        this.contacts.add(contact);
    }
}
