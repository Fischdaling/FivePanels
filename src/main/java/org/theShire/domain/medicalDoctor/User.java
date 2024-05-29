package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;
import org.theShire.domain.media.Media;
import org.theShire.foundation.Knowledges;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
    private Set<Knowledges> specialization;



    public User(Password password, Email email, UserProfile profile, Set<Knowledges> specialization) {
        super();
        contacts = new HashSet<>();
        chats = new HashSet<>();
        ownedCases = new HashSet<>();
        memberOfCase = new HashSet<>();
        this.password = password;
        this.email = email;
        this.profile = profile;
        this.specialization = specialization;
    }
    public User(UUID uuid,Password password, Email email, UserProfile profile, Set<Knowledges> specialization) {
        super(uuid);
        contacts = new HashSet<>();
        chats = new HashSet<>();
        ownedCases = new HashSet<>();
        memberOfCase = new HashSet<>();
        this.password = password;
        this.email = email;
        this.profile = profile;
        this.specialization = specialization;
    }


    public User(UUID uuid, Instant createdAt, Instant updatedAt, Email email, Password password, UserProfile profile, int score, Set<UserRelationShip> contacts, Set<Chat> chats, Set<Case> ownedCases, Set<Case> memberOfCase, Set<Knowledges> specialization) {
        super(uuid, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.score = score;
        this.contacts = contacts;
        this.chats = chats;
        this.ownedCases = ownedCases;
        this.memberOfCase = memberOfCase;
        this.specialization = specialization;
    }

    // SETTER & GETTER -----------------------------------------------------------

    public void setSpecialization(Knowledges[] specialization) {
        this.specialization = new HashSet<>(Arrays.asList(isNotNull(specialization,"specialization",exTypeUser)));
    }
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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    // Methods ------------------------------------------------------------


    public void addChat(Chat chat) {
        this.chats.add(isNotInCollection(chat, chats, "Chat already in Set", exTypeUser));
    }

    public void addOwnedCase(Case medCase){
        this.ownedCases.add(medCase);
    }

    public void addMemberOfCase(Case medCase){
        this.memberOfCase.add(medCase);
    }


    public void removeCase(Case medCase){
        if(memberOfCase.contains(medCase)) {
            this.memberOfCase.remove(medCase);
        } else if (ownedCases.contains(medCase)) {
            this.ownedCases.remove(medCase);
        }else{
            throw new MedicalDoctorException("Unknown medical case");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder().append(System.lineSeparator());
        isNotNull(profile, "profile", exTypeUser);
        sb.append("ID: ").append(getEntityId()).append(System.lineSeparator());
        sb.append(profile).append(System.lineSeparator());
        sb.append("email: ").append(email).append(System.lineSeparator());
        sb.append("password: ").append(password).append(System.lineSeparator());
        sb.append("score: ").append(score).append(System.lineSeparator());
        sb.append("contacts: ").append(contacts).append(System.lineSeparator());
        sb.append("chats: ").append(chats).append(System.lineSeparator());
        sb.append("specializations: ").append(specialization).append(System.lineSeparator());
        sb.append("ownedCases: ").append(ownedCases.stream().map(Case::getTitle).findAny().orElse(null)).append(System.lineSeparator());
        sb.append("memberOfCase: ").append(memberOfCase.stream().map(Case::getTitle).findAny().orElse(null)).append(System.lineSeparator());
        return sb.toString();
    }
    @Override
    public String toCSVString(){
        final StringBuilder sb = new StringBuilder(super.toCSVString());
        sb.append(email).append(";");
        sb.append(password).append(";");
        sb.append(score).append(";");
        sb.append(contacts).append(";");
        sb.append(chats.stream().map(Chat::getEntityId).collect(Collectors.toSet())).append(";");
        sb.append(specialization).append(";");
        sb.append(ownedCases.stream().map(Case::getEntityId).findAny().orElse(null)).append(";");
        sb.append(memberOfCase.stream().map(Case::getEntityId).findAny().orElse(null)).append(";");
        sb.append(profile.toCSVString());
        sb.append(System.lineSeparator());
        return sb.toString();
    }


}
