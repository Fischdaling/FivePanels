package org.theShire.repository;

import org.theShire.domain.exception.MediaException;
import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.exception.MessengerException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalCase.Vote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.Name;
import org.theShire.foundation.Knowledges;


import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.userRepo;

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
        UUID entityId = UUID.fromString(parts[0]);
        Instant createdAt = Instant.parse(parts[1]);
        Instant updatedAt = Instant.parse(parts[2]);
        User owner = userRepo.findByID(UUID.fromString(parts[3]));
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

    private List<Content> parseContent(String part) {
        return Arrays.stream(part.split(","))
                .map(text -> new Content(new ContentText(text)))
                .collect(Collectors.toList());
    }

    private CaseVote parseCaseVote(String part) {
        String[] parts = part.split(",");
        LinkedHashSet<Answer> answers = parseAnswers(parts[0]);
        HashMap<UUID, Set<Vote>> votes = parseVotes(parts[1]);
        double percentCount = Double.parseDouble(parts[2]);

        return new CaseVote(answers, votes, percentCount);
    }



    //TODO BIIIG HASHMAP LOOKING
    private HashMap<UUID, Set<Vote>> parseVotes(String part) {
        return new HashMap<>();
    }

    private LinkedHashSet<Answer> parseAnswers(String part) {
        String[] parts = part.split(",");
        LinkedHashSet<Answer> answers = new LinkedHashSet<>();
        for (int i = 0; i < parts.length; i++) {
            String[] partsParts = parts[i].split("_");
            UUID uuid = UUID.fromString(partsParts[0]);
            Instant createdAt = Instant.parse(partsParts[1]);
            Instant updatedAt = Instant.parse(partsParts[2]);
            Name name = new Name(parts[3]);
            answers.add(new Answer(uuid,createdAt,updatedAt,name));
        }
        return new LinkedHashSet<>();
    }

    private Set<UUID> parseUsersLiked(String part) {
        String[] parts = part.split(", ");
        String[] partsEdit = new String[parts.length];
        Set<UUID> user = new HashSet<>();
        for (int i = 0; i < parts.length; i++) {
            partsEdit[i] = parts[i].replace("[","");
            partsEdit[i] = partsEdit[i].replace("]","");
            if (partsEdit[i].isEmpty()) {
                return null;
            }
            user.add(UUID.fromString(partsEdit[i]));
        }
        return user;
    }

    private Set<User> parseMembers(String part) {

        String[] parts = part.split(", ");
        String[] partsEdit = new String[parts.length];
        Set<User> user = new HashSet<>();
        for (int i = 0; i < parts.length; i++) {
            partsEdit[i] = parts[i].replace("[","");
            partsEdit[i] = partsEdit[i].replace("]","");
            user.add(userRepo.findByID(UUID.fromString(partsEdit[i])));
        }
        return user;
    }

    private Set<Knowledges> parseKnowledges(String part) {
        String[] parts = part.split(",");
        Set<Knowledges> knowledges = new HashSet<>();
        for (String str : parts) {
            str = str.trim();
            str = str.replace("[","");
            str = str.replace("]","");
            knowledges.add(new Knowledges(str));

        }
        return knowledges;
    }
}
