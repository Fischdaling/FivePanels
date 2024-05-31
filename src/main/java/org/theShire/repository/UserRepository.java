package org.theShire.repository;

import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;
import org.theShire.foundation.DomainAssertion;
import org.theShire.foundation.Knowledges;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;

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

        DomainAssertion.isTrue(parts.length == 17, () -> "Error: CSV lines not matching", exTypeUser);
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
        Language language = new Language(parts[11]);
        Location location = new Location(parts[12]);
        Media profilePicture = parseMedia(parts[13]);
        Name firstName = new Name(parts[14]);
        Name lastName = new Name(parts[15]);
        List<EducationalTitle> educationalTitles = parseEducationalTitles(parts[16]);

        UserProfile profile = new UserProfile(language, location, profilePicture, firstName, lastName, educationalTitles);
        User user = new User(entityId, createdAt, updatedAt, email, password, profile, score, contacts, chats, ownedCases, memberOfCase, specializations);
        user.setScore(score);
        return user;
    }

    private Set<UserRelationShip> parseContacts(String value) {
        // TODO
        return new HashSet<>();
    }

    private Set<Chat> parseChats(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(s -> !s.trim().isEmpty())
                .map(s -> messengerRepo.findByID(UUID.fromString(s.trim())))
                .collect(Collectors.toSet());
    }

    private Set<Knowledges> parseSpecializations(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(s -> !s.trim().isEmpty())
                .map(Knowledges::new)
                .collect(Collectors.toSet());
    }

    private Set<Case> parseCases(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(s -> !s.trim().isEmpty())
                .map(s -> caseRepo.findByID(UUID.fromString(s.trim())))
                .collect(Collectors.toSet());
    }

    private List<EducationalTitle> parseEducationalTitles(String value) {
        return Arrays.stream(value.replaceAll("[\\[\\]]", "").split(","))
                .filter(s -> !s.trim().isEmpty())
                .map(EducationalTitle::new)
                .collect(Collectors.toList());
    }

    private Media parseMedia(String value) {
        String[] parts = value.split(",");
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);
        String altText = parts[2];
        String resolution = parts[3];
        return new Media(width, height, altText, resolution);
    }

}
