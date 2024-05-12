package org.theShire.domain.MedicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.MedicalCase.Case;
import org.theShire.domain.Messenger.Chat;

import java.time.Instant;
import java.util.Set;

public class User extends BaseEntity {

    private UserProfile profile;
    private int score;
    private Set<UserRelationShip> contacts;
    private Set<Chat> chats;
    private Set<Case> ownedCases;
    private Set<Case> memberOfCase;


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

    public void setContacts(Set<UserRelationShip> contacts) {
        //TODO ASSERTION
        this.contacts = contacts;
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
        //TODO ASSERTION
        this.chats.add(chat);
    }
}
