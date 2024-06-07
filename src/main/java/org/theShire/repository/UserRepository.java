package org.theShire.repository;

import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.medicalDoctor.Relation;
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
import static org.theShire.service.UserService.userRepo;

public class UserRepository extends AbstractRepository<User> {

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (User user : entryMap.values()) {
                writer.write(user.toCSVString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("File %s has a problem", filepath), e);
        }
    }

    @Override
    public void loadEntryMap(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromCSVString(line);
                entryMap.put(user.getEntityId(), user);
            }
        } catch (IOException e) {
            throw new MedicalDoctorException(e.getMessage());
        }
    }


}
