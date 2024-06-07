package org.theShire.repository;

import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalCase.Vote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.foundation.Knowledges;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.isNotNull;
import static org.theShire.presentation.UniversalPresentation.enterUUID;
import static org.theShire.service.UserService.userRepo;

public class CaseRepository extends AbstractRepository<Case> {
    public Set<Case> getCaseByOwner(UUID ownerId) {
        return entryMap.values().stream()
                .filter(aCase -> aCase.getOwner().getEntityId().equals(ownerId))
                .collect(Collectors.toSet());
    }

    @Override
    public void saveEntryMap(String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (Case medCase : entryMap.values()) {
                writer.write(medCase.toCSVString());
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
                Case medCase = Case.fromCSVString(line);
                entryMap.put(medCase.getEntityId(), medCase);
            }
        } catch (IOException e) {
            throw new MedicalCaseException(e.getMessage());
        }
    }

}
