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
import org.theShire.presentation.Main;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.greaterEqualsZero;
import static org.theShire.foundation.DomainAssertion.isTrue;
import static org.theShire.presentation.Main.*;

public class UserRepository extends AbstractRepository<User>{

    public Set<User> findByName(Name name) {
        return entryMap.values().stream()
                .filter(user -> user.getProfile().getFirstName().value().equalsIgnoreCase(name.value()))
                .collect(Collectors.toSet());
    }

    public Optional<User> findByEmail(Email email) {
        return entryMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void saveEntryMap(String filepath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            entryMap.values().stream()
                    .map(User::toCSVString)
                    .forEach(csvFile -> {
                        try {
                            bufferedWriter.write(csvFile);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
            System.out.println("Successfully saved " + filepath);
        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s has a problem", filepath), e);
        }
    }

    @Override
    public void loadEntryMap(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            bufferedReader.lines()
                    .map(this::parseUser)
                    .forEach(user -> entryMap.put(user.getEntityId(), user));
        } catch (IOException e) {
            throw new MedicalDoctorException(e.getMessage());
        }
    }

    private User parseUser(String line) {
        String[] parts = line.split(";");

//        isTrue(parts.length == 17, () -> "Error: CSV lines not matching", exTypeUser);
        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);
        Email email = new Email(parts[3]);
        Password password = new Password(parts[4]);
        int score = Integer.parseInt(parts[5]);
        Set<UserRelationShip> contacts = parseContacts(parts[6]);
        Set<Chat> chats = parseChats(parts[7]);
        Set<Knowledges> specializations = parseSpecializations(parts[8]);
        Set<Case> ownedCases = parseCases(parts[9]);
        Set<Case> memberOfCase = parseCases(parts[10]);
        Name firstName = new Name(parts[11]);
        Name lastName = new Name(parts[12]);
        List<EducationalTitle> educationalTitles = parseEducationalTitles(parts[13]);
        Media profilePicture = parseMedia(parts[14]);
        Language language = new Language(parts[15]);
        Location location = new Location(parts[16]);

        UserProfile profile = new UserProfile(language, location, profilePicture, firstName, lastName, educationalTitles);
        User user = new User(entityId, createdAt, updatedAt, email, password, profile, score, contacts, chats, ownedCases, memberOfCase, specializations);
        user.setScore(score);
        return user;
    }

    private Media parseMedia(String part) {
        String[] parts = part.split(",");
        return new Media(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),parts[2],parts[3]);
    }

    private Set<UserRelationShip> parseContacts(String value) {
        String[] parts = value.split(";");
        if (parts.length >0) {
            return null;
        }
        return Arrays.stream(value.split(","))
                .map(s -> findByID(UUID.fromString(s)).getContacts())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<Chat> parseChats(String value) {
        String[] parts = value.split(";");
        if (parts.length >0) {
            return null;
        }
        return Arrays.stream(value.split(","))
                .map(s -> messangerRepo.findByID(UUID.fromString(s)))
                .collect(Collectors.toSet());
    }

    private Set<Knowledges> parseSpecializations(String value) {

        String[] parts = value.split(",");
        Set<Knowledges> knowledges = new HashSet<>();
        for (String part : parts) {
            part = part.trim();
            part = part.replace("[","");
            part = part.replace("]","");
            knowledges.add(new Knowledges(part));

        }
        return knowledges;
    }

    private Set<Case> parseCases(String value) {
        value = value.replace("[","");
        value = value.replace("]","");
        if (value.trim().isEmpty() || value.trim().isBlank() || value.equals("null")){
            return new HashSet<>();
        }
        if (caseRepo.entryMap.containsKey(UUID.fromString(value))) {
            return Arrays.stream(value.split(","))
                    .map(s -> caseRepo.findByID(UUID.fromString(s)))
                    .collect(Collectors.toSet());
        }else {
            Set<Case> cases = new HashSet<>();
            System.out.println("case import failed input manuel: ");
            System.out.println("how many cases do you want to add");
            int j = scanner.nextInt();
            for (int i = 0; i < j; i++) {
                UUID caseId = Main.enterUUID("Please enter the caseId");
                cases.add(caseRepo.findByID(caseId));
            }

            return cases;
        }

    }

    private List<EducationalTitle> parseEducationalTitles(String value) {
        value = value.replace("[","");
        value = value.replace("]","");
        return Arrays.stream(value.split(","))
                .map(EducationalTitle::new)
                .collect(Collectors.toList());
    }
}
