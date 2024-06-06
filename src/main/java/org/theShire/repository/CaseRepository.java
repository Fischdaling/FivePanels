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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            entryMap.values().stream()
                    .map(Case::toCSVString)
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
                    .map(this::parseCase)
                    .forEach(medCase -> entryMap.put(medCase.getEntityId(), medCase));
        } catch (IOException e) {
            throw new MedicalCaseException(e.getMessage());
        }
    }

    private Case parseCase(String line) {
        String[] parts = line.split(";");
        if (parts.length != 12) {
            throw new MedicalCaseException("Invalid CSV format");
        }

        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);
        User owner = getOwnerID(parts[3]);
        String title = parts[4];
        List<Content> content = parseContent(parts[5]);
        Set<Knowledges> knowledges = parseKnowledges(parts[6]);
        int viewCount = Integer.parseInt(parts[7]);
        Set<User> members = parseMembers(parts[8]);
        int likeCount = Integer.parseInt(parts[9]);
        Set<UUID> usersLiked = parseUsersLiked(parts[10]);
        CaseVote caseVote = parseCaseVote(parts[11]);

        return new Case(entityId, createdAt, updatedAt, title, content, viewCount, knowledges, owner, members, likeCount, usersLiked, caseVote);
    }


    private User getOwnerID(String part) {
        UUID ownerId = UUID.fromString(part);
        User owner = userRepo.findByID(ownerId);
        if (owner == null) {
            throw new MedicalCaseException("Owner not found");
        }
        return owner;
    }

    private List<Content> parseContent(String part) {
        return Arrays.stream(part.split(","))
                .map(text -> new Content(new ContentText(text)))
                .collect(Collectors.toList());
    }

    private CaseVote parseCaseVote(String part) {
        String[] parts = part.split(",");
        LinkedHashSet<Answer> answers = parseAnswers(parts[0]);
        HashMap<UUID, Set<Vote>> votes = parseVotes(parts[1]);
        return new CaseVote(answers, votes);
    }

    private HashMap<UUID, Set<Vote>> parseVotes(String part) {
        //TODO
        return new HashMap<>();
    }


    private LinkedHashSet<Answer> parseAnswers(String part) {
        return Arrays.stream(part.split(","))
                .map(Answer::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<UUID> parseUsersLiked(String part) {
        return Arrays.stream(part.replaceAll("[\\[\\]]", "").split(","))
                .filter(str -> !str.isEmpty())
                .map(UUID::fromString)
                .collect(Collectors.toSet());
    }



    private Set<User> parseMembers(String part) {
        return Arrays.stream(part.replaceAll("[\\[\\]]", "").split(","))
                .map(UUID::fromString)
                .map(userRepo::findByID)
                .collect(Collectors.toSet());
    }

    private Set<Knowledges> parseKnowledges(String part) {
        return Arrays.stream(part.split(","))
                .map(Knowledges::new)
                .collect(Collectors.toSet());
    }
}
