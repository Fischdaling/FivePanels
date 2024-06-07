package org.theShire.domain.medicalDoctor;

import org.theShire.domain.BaseEntity;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.*;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userRepo;

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
    private Set<Relation> contacts;
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
    //Test
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

    public User(UUID uuid, Instant createdAt, Instant updatedAt, Email email, Password password, UserProfile profile, int score, Set<Relation> contacts, Set<Chat> chats, Set<Case> ownedCases, Set<Case> memberOfCase, Set<Knowledges> specialization) {
        super(uuid, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.score = score;
        this.contacts = contacts;
        this.chats = chats;
        if (ownedCases == null){
            ownedCases = new HashSet<>();
        }
        this.ownedCases = ownedCases;
        if (memberOfCase == null){
            memberOfCase = new HashSet<>();
        }
        this.memberOfCase = memberOfCase;
        this.specialization = specialization;
    }

    // SETTER & GETTER -----------------------------------------------------------


    public UserProfile getProfile() {
        return profile;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public Set<Chat> getChats(){
        return chats;
    }

    // Methods ------------------------------------------------------------


    public void addChat(Chat chat) {
        this.chats.add(isNotInCollection(chat, chats, "Chat already in Set", exTypeUser));
    }

    public void addOwnedCase(Case medCase){
        this.ownedCases.add(isNotNull(medCase,"medCase",exTypeUser));
    }

    public void addMemberOfCase(Case medCase){
        this.memberOfCase.add(isNotInCollection(medCase,memberOfCase,"memberOfCase",exTypeUser));
    }


    public void removeCase(Case medCase) {
        if (memberOfCase.contains(medCase)) {
            this.memberOfCase.remove(medCase);
        } else if (ownedCases.contains(medCase)) {
            this.ownedCases.remove(medCase);
        } else {
            throw new MedicalDoctorException("Unknown medical case");
        }
    }


    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Set<Knowledges> getSpecialization() {
        return specialization;
    }
    public Password getPassword() {
        return password;
    }

    public void addContacts(Relation relation) {
        contacts.add(isNotInCollection(relation,contacts,"user",exTypeUser));
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder().append(System.lineSeparator());
        isNotNull(profile, "profile", exTypeUser);
        sb.append("ID: ").append(getEntityId()).append(System.lineSeparator());
        sb.append(profile).append(System.lineSeparator());
        sb.append("email: ").append(email).append(System.lineSeparator());
        sb.append("password: ").append(password).append(System.lineSeparator());
        sb.append("score: ").append(getScore()).append(System.lineSeparator());
        sb.append("chats: ").append(chats).append(System.lineSeparator());
        sb.append("specializations: ").append(specialization).append(System.lineSeparator());
        sb.append("ownedCases: ").append(ownedCases.stream().map(Case::getTitle).findAny().orElse(null)).append(System.lineSeparator());
        sb.append("memberOfCase: ").append(memberOfCase.stream().map(Case::getTitle).findAny().orElse(null)).append(System.lineSeparator());
        return sb.toString();
    }

    @Override
    public String toCSVString() {
        final StringBuilder sb = new StringBuilder(super.toCSVString());
        sb.append(email).append(";");
        sb.append(password).append(";");
        sb.append(score).append(";");
        sb.append(contacts.stream().map(Relation::toString).collect(Collectors.toSet())).append(";");
        sb.append(chats.stream().map(Chat::getEntityId).collect(Collectors.toSet())).append(";");
        sb.append(specialization).append(";");
        sb.append(ownedCases.stream().map(Case::getEntityId).findAny().orElse(null)).append(";");
        sb.append(memberOfCase.stream().map(Case::getEntityId).findAny().orElse(null)).append(";");
        sb.append(profile.toCSVString());
        sb.append(System.lineSeparator());
        return sb.toString();
    }


    public static User fromCSVString(String csv) {
        String[] parts = csv.split(";");
        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);
        Email email = new Email(parts[3]);
        Password password = new Password(parts[4]);
        int score = Integer.parseInt(parts[5]);
        Set<Relation> contacts = parseContacts(parts[6]);
        Set<Chat> chats = parseChats(parts[7]);
        Set<Knowledges> specializations = parseSpecializations(parts[8]);
        Set<Case> ownedCases = parseCases(parts[9]);
        Set<Case> memberOfCase = parseCases(parts[10]);
        Name firstName = new Name(parts[11]);
        Name lastName = new Name(parts[12]);
        List<EducationalTitle> educationalTitles = parseEducationalTitles(parts[13]);
        Media profilePicture = parseMedia(parts[14]);
        Location location = new Location(parts[15]);
        Language language = new Language(parts[16]);

        UserProfile profile = new UserProfile(language, location, profilePicture, firstName, lastName, educationalTitles);
        return new User(entityId, createdAt, updatedAt, email, password, profile, score, contacts, chats, ownedCases, memberOfCase, specializations);
    }

    private static Set<Relation> parseContacts(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(str -> !str.isEmpty())
                .map(relStr -> {
                    String[] relParts = relStr.split(":");
                    UUID userId1 = UUID.fromString(relParts[0]);
                    UUID userId2 = UUID.fromString(relParts[1]);
                    Relation.RelationType type = Relation.RelationType.valueOf(relParts[2]);

                    User user1 = userRepo.findByID(userId1);
                    User user2 = userRepo.findByID(userId2);

                    if (user1 == null || user2 == null) {
                        throw new MedicalDoctorException("sender and receiver cannot be null");
                    }

                    return new Relation(user1, user2, type);
                })
                .collect(Collectors.toSet());
    }

    private static Set<Chat> parseChats(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(str -> !str.isEmpty())
                .map(uuid -> messengerRepo.findByID(UUID.fromString(uuid)))
                .collect(Collectors.toSet());
    }

    private static Set<Knowledges> parseSpecializations(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(str -> !str.isEmpty())
                .map(Knowledges::new)
                .collect(Collectors.toSet());
    }

    private static Set<Case> parseCases(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(str -> !str.isEmpty())
                .map(uuid -> caseRepo.findByID(UUID.fromString(uuid)))
                .collect(Collectors.toSet());
    }

    private static List<EducationalTitle> parseEducationalTitles(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(str -> !str.isEmpty())
                .map(String::trim)
                .map(EducationalTitle::new)
                .collect(Collectors.toList());
    }

    private static Media parseMedia(String value) {
        String altText = value.substring(6);
        return new Media(altText);
    }

}
