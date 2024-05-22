package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;

import java.time.Instant;
import java.util.*;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.*;

public class User extends BaseEntity {
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



    public User(Password password, Email email, UserProfile profile) {
        super();
        contacts = new HashSet<>();
        chats = new HashSet<>();
        ownedCases = new HashSet<>();
        memberOfCase = new HashSet<>();
        this.password = password;
        this.email = email;
        this.profile = profile;
    }


    // SETTER & GETTER -----------------------------------------------------------
    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = isNotNull(profile, "profile", exTypeUser);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = greaterEqualsZero(score, "score", exTypeUser);
    }

    public Set<UserRelationShip> getContacts() {
        return contacts;
    }


    public Chat getChatByID(UUID id) {
        return chats.stream().filter(chat -> chat.getEntityId().equals(id)).findFirst().orElse(null);
    }

    public List<Chat> getChatByUser(User name) {
        return chats.stream().filter(chat -> chat.getPeople().contains(name)).toList();
    }


    public Set<Case> getOwnedCases() {
        return ownedCases;
    }


    public Set<Case> isMemberOfCases() {
        return memberOfCase;
    }

    // Methods ------------------------------------------------------------
    public void createCase(String title, List<Content> content, User... members) {

        this.ownedCases.add(new Case(this, title, content, members));
    }

    public void addChat(Chat chat) {
        this.chats.add(isInCollection(chat, chats, "Chat already in Set", exTypeUser));
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{").append(System.lineSeparator());
        isNotNull(profile, "profile", exTypeUser);
        sb.append("profile=").append(profile).append(System.lineSeparator());
        sb.append("email=").append(email).append(System.lineSeparator());
        sb.append("password=").append(password).append(System.lineSeparator());
        sb.append("profile=").append(profile).append(System.lineSeparator());
        sb.append("score=").append(score).append(System.lineSeparator());
        sb.append("contacts=").append(contacts).append(System.lineSeparator());
        sb.append("chats=").append(chats).append(System.lineSeparator());
//        sb.append("ownedCases=").append(ownedCases).append(System.lineSeparator());
        sb.append("memberOfCase=").append(memberOfCase).append(System.lineSeparator());
        sb.append('}');
        return sb.toString();
    }


}
