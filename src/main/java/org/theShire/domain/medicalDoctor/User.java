package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;
import org.theShire.domain.media.Media;

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


    public User() {
        super(Instant.now());
    }

    public User(Password password, Email email, UserProfile profile) {
        super(Instant.now());
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
    public void createCase(String title, List<Content> content, User... members) {

        this.ownedCases.add(new Case(this, title, content, members));
    }

    public void addChat(Chat chat) {
        this.chats.add(isNotInCollection(chat, chats, "Chat already in Set", exTypeUser));
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
        sb.append("ownedCases=").append(ownedCases).append(System.lineSeparator());
        sb.append("memberOfCase=").append(memberOfCase).append(System.lineSeparator());
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        //TODO Relation && builder
        //Creating ProfilePic
        Media media = new Media(2000,1500,"I am a Picture", "2000x1500");
        //Create Profile
        UserProfile profile = new UserProfile(new Language("German"),
                new Location("Gondor"),media,new Name("Boromir"),
                new Name("Aragorn"),new EducationalTitle("Dr."),
                new EducationalTitle("arathorn"));


        //Create User
        User user = new User(new Password("Spengergasse"),
                new Email("Boromir@gamil.com"),profile);


        //Create ProfilePic
        Media media1 = new Media(1500,100,"I am a Father", "1500x100");
        //Create Profile
        UserProfile profile1 = new UserProfile(new Language("German"),
                new Location("Gondor"),media1,new Name("Aarathorn"),
                new Name("Aragorn"),new EducationalTitle("Dr."),
                new EducationalTitle("Arathorn"),new EducationalTitle("mag"));
        //Create USer
        User user1 = new User(new Password("Spengergasse123"),
                new Email("Arathorn@gamil.com"),profile1);

        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200,100,"My First Media", "200x100")));

        //Create a Case with user 1 as member and user as owner
        user.createCase("my First Case", contents,user1);

        // TODO NO GOOD Solution
        UserRelationShip relationShip = new UserRelationShip(user,user1);
        relationShip.acceptRequest(user.getEntityId(),user1.getEntityId());

        for (Chat chat : user.getChats()) {
            chat.sendMessage(new Message(user.getEntityId(),new Content(new ContentText("Hi"))));
        }
        for (Chat chat : user1.getChats()) {
            chat.sendMessage(new Message(user.getEntityId(),new Content(new ContentText("Yo"))));
        }

        System.out.println(user.chats);

    }

}
