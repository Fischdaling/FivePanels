package org.theShire.repository;

import org.theShire.domain.exception.MediaException;
import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.greaterEqualsZero;
import static org.theShire.foundation.DomainAssertion.isTrue;
import static org.theShire.presentation.Main.*;

public class UserRepository extends AbstractRepository<User>{

    public Optional<User> findByName(Name name) {
        return entryMap.values().stream()
                .filter(user -> user.getProfile().getFirstName().value().equalsIgnoreCase(name.value()))
                .findFirst();
    }

    public Optional<User> findByEmail(Email email) {
        return entryMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
    @Override
    public void saveEntryMap(String filepath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {

            for (User value : entryMap.values()) {
                String csvFile = value.toCSVString();
                bufferedWriter.write(csvFile);
            }
            System.out.println("Successfully saved " + filepath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s is not found", filepath), e);

        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s has a problem", filepath), e);
        }
    }
    @Override
    public void loadEntryMap(String filepath) {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

//                    isTrue(parts.length != 17,()->"Error: CSV lines not matching",exTypeUser);
                    UUID entityId = UUID.fromString(parts[0]);
                    Instant createdAt = Instant.parse(parts[1]);
                    Instant updatedAt = Instant.parse(parts[2]);
                    Email email = new Email(parts[3]);
                    Password password = new Password( parts[4]);
                    int score = Integer.parseInt(parts[5]);
                    Set<UserRelationShip> contacts = saveContacts(parts[6]);
                    Set<Chat> chats = saveChats(parts[7]);
                    Set<Knowledges> specializations = saveSpecializations(parts[8]);
                    Set<Case> ownedCases = saveCases(parts[9]);
                    Set<Case> memberOfCase = saveCases(parts[10]);
                    Language language = new Language(parts[11]);
                    Location location = new Location(parts[12]);
                    Media profilePicture = new Media(parts[13]);
                    Name firstName = new Name(parts[14]);
                    Name lastName = new Name(parts[15]);
                    List<EducationalTitle> educationalTitles = saveEducationalTitles(parts[16]);

                    UserProfile profile = new UserProfile(language, location, profilePicture, firstName, lastName, educationalTitles);
                    User user = new User(entityId, createdAt,updatedAt, email, password, profile, score,contacts,chats,ownedCases,memberOfCase,specializations);
                    user.setScore(score);
                    // Set other fields accordingly
                    users.add(user);
            }
        } catch (FileNotFoundException e) {
            throw new MedicalDoctorException(e.getMessage());
        } catch (IOException e) {
            throw new MedicalDoctorException(e.getMessage());
        }
    }
//TODO
    private Set<UserRelationShip> saveContacts(String value) {
        Set<String> str = Arrays.stream(value.split(",")).collect(Collectors.toSet());
        return str.stream().map((s -> findByID(UUID.fromString(s)).getContacts())).findAny().orElse(Collections.emptySet());
    }

    private Set<Chat> saveChats(String value) {
        Set<String> str = Arrays.stream(value.split(",")).collect(Collectors.toSet());
        return str.stream().map((s -> messangerRepo.findByID(UUID.fromString(s)))).collect(Collectors.toSet());
    }

    private Set<Knowledges> saveSpecializations(String value) {
        return Arrays.stream(value.split(","))
                .map(Knowledges::new)
                .collect(Collectors.toSet());
    }

    private Set<Case> saveCases(String value) {
        Set<String> str = Arrays.stream(value.split(",")).collect(Collectors.toSet());
        return str.stream().map((s -> caseRepo.findByID(UUID.fromString(s)))).collect(Collectors.toSet());
    }

    private List<EducationalTitle> saveEducationalTitles(String value) {
        return Arrays.stream(value.split(","))
                .map(EducationalTitle::new)
                .collect(Collectors.toList());
    }
}
