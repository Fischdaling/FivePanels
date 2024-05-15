package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;
import org.theShire.domain.media.Media;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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


    public User() {
        super(Instant.now());
    }

    public User(Password password, Email email, UserProfile profile) {
        super(Instant.now());
        this.password = password;
        this.email = email;
        this.profile = profile;
        Set<UserRelationShip> contacts = new HashSet<>();
        Set<Chat> chats = new HashSet<>();
        Set<Case> ownedCases = new HashSet<>();
        Set<Case> memberOfCase = new HashSet<>();
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
    public void createCase(String title, List<Content> content, UUID... members) {

        this.ownedCases.add(new Case(this.getEntityId(), title, content, members));
    }

    public void addChat(Chat chat) {
        this.chats.add(isNotInCollection(chat, chats, "Chat already in Set", exTypeUser));
    }

    public void addContact(UserRelationShip contact) {
        isNotInCollection(contact, contacts, "Contact already in Set", exTypeUser);
        this.contacts.add(contact);
    }

    public static void main(String[] args) {
        //TODO Relation & Compilor
        Media media = new Media(2000,1500,"I am a Picture", "2000x1500");

        UserProfile profile = new UserProfile(new Language("German"),
                new Location("Gondor"),media,new Name("Boromir"),
                new Name("Aragorn"),new EducationalTitle("Dr."),
                new EducationalTitle("arathorn"));



        User user = new User(new Password("Spengergasse"),
                new Email("Boromir@gamil.com"),profile);



        Media media1 = new Media(1500,100,"I am a Father", "1500x100");

        UserProfile profile1 = new UserProfile(new Language("German"),
                new Location("Gondor"),media1,new Name("Aarathorn"),
                new Name("Aragorn"),new EducationalTitle("Dr."),
                new EducationalTitle("Arathorn"),new EducationalTitle("mag"));



        User user1 = new User(new Password("Spengergasse123"),
                new Email("Arathorn@gamil.com"),profile1);

        System.out.println(user1);
        System.out.println(user);

    }

}
