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
import org.theShire.foundation.Knowledges;


import java.io.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.userRepo;

public class CaseRepository extends AbstractRepository<Case> {
        public Set<Case> getCaseByOwner(UUID ownerId){
            return entryMap.values().stream().
                    filter(aCase -> aCase.getOwner().getEntityId().equals(ownerId)).
                    collect(Collectors.toSet());
        }

    @Override
    public void saveEntryMap(String filepath) {
        try(BufferedWriter bufferedWriter =new BufferedWriter(new FileWriter(filepath))) {

            for (Case value : entryMap.values()) {
                String csvFile = value.toCSVString();

                bufferedWriter.write(csvFile);
            }
            System.out.println("Successfully saved " + filepath);
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(String.format("File %s is not found",filepath), e);

        }
        catch (IOException e){
            throw new RuntimeException(String.format("File %s has a problem",filepath), e);
        }
    }

    @Override
    public void loadEntryMap(String filepath) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(";");
                    UUID entityId = UUID.fromString(parts[0]);
                    Instant createdAt = Instant.parse(parts[1]);
                    Instant updatedAt = Instant.parse(parts[2]);
                    User owner = userRepo.findByID(UUID.fromString(parts[3]));
                    String title = parts[4];
                    Set<Knowledges> knowledges = saveKnowledges(parts[5]);
                    int viewCount = Integer.parseInt(parts[6]);
                    Set<User> members = saveMembers(parts[7]);
                    int likeCount = Integer.parseInt(parts[8]);
                    Set<UUID> usersLiked = saveUsersLiked(parts[9]);
                    CaseVote caseVote = saveCaseVote(parts[10]);

                    new Case(entityId,createdAt,updatedAt);
                }
        } catch (FileNotFoundException e) {
                throw new MedicalCaseException(e.getMessage());
            } catch (IOException e) {
                throw new MediaException(e.getMessage());
            }
    }

    private Content saveContent(String contentStr) {
        if (contentStr.startsWith("text:")) {
            String text = contentStr.substring(5);
            return new Content(new ContentText(text));
        } else if (contentStr.startsWith("media:")) {
            String media = contentStr.substring(6);
            return new Content(new Media(media));
        } else {
            throw new MessengerException("Unknown content format: " + contentStr);
        }
    }

    private CaseVote saveCaseVote(String part) {
        String[] parts = part.split(";");
        LinkedHashSet<Answer> answers = Arrays.stream(parts[0].split(","))
                .map(Answer::toCSVString)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        HashMap<UUID, Set<Vote>> votes = Arrays.stream(parts[1].split(";"))
                .map(entry -> {
                    String[] entryParts = entry.split(":");
                    UUID voter = UUID.fromString(entryParts[0]);
                    Set<Vote> voteSet = Arrays.stream(entryParts[1].split(","))
                            .map(Vote::fromCSVString)
                            .collect(Collectors.toSet());
                    return new AbstractMap.SimpleEntry<>(voter, voteSet);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        int maxAnswers = Integer.parseInt(parts[2]);
        double percentCount = Double.parseDouble(parts[3]);
        double maxPercentCount = Double.parseDouble(parts[4]);

        CaseVote caseVote = new CaseVote(answers, votes);
        caseVote.setMaxAnswers(maxAnswers);
        caseVote.setpercentCount(percentCount);

        return caseVote;
    }
    private Set<UUID> saveUsersLiked(String part) {
        Set<String> str = Arrays.stream(part.split(",")).collect(Collectors.toSet());
        return str.stream().map(UUID::fromString).collect(Collectors.toSet());

    }

    private Set<User> saveMembers(String part) {
        Set<String> str = Arrays.stream(part.split(",")).collect(Collectors.toSet());
        return str.stream().map((s -> userRepo.findByID(UUID.fromString(s)))).collect(Collectors.toSet());
    }

    private Set<Knowledges> saveKnowledges(String part) {
        return Arrays.stream(part.split(","))
                .map(Knowledges::new)
                .collect(Collectors.toSet());
        }


}
