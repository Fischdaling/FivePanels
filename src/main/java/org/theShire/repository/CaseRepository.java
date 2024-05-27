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
                    List<Content> content = saveContent(parts[5]);
                    Set<Knowledges> knowledges = saveKnowledges(parts[6]);
                    int viewCount = Integer.parseInt(parts[7]);
                    Set<User> members = saveMembers(parts[8]);
                    int likeCount = Integer.parseInt(parts[9]);
                    Set<UUID> usersLiked = saveUsersLiked(parts[10]);
                    CaseVote caseVote = saveCaseVote(parts[11]);

                    new Case(entityId,createdAt,updatedAt,title,content,viewCount,knowledges,owner,members,likeCount,usersLiked,caseVote);
                }
        } catch (FileNotFoundException e) {
                throw new MedicalCaseException(e.getMessage());
            } catch (IOException e) {
                throw new MediaException(e.getMessage());
            }
    }

    private List<Content> saveContent(String part) {
            List<Content> content = new ArrayList<>();
        String[] parts = part.split(",");
        for (int i = 0; i < parts.length; i++) {
            String text = parts[i];
            content.add( new Content(new ContentText(text)));
        }
        return content;
    }

    private CaseVote saveCaseVote(String part) {
        String[] parts = part.split(",");
        LinkedHashSet<Answer> answer = saveAnswers(parts[0]);
        HashMap<UUID, Set<Vote>> votes = saveVotes(parts[1]);
        int percentCount = Integer.parseInt(parts[3]);

        return new CaseVote(answer,votes,percentCount);
    }

    private HashMap<UUID, Set<Vote>> saveVotes(String part) {
            return  new HashMap<>();
    }

    private LinkedHashSet<Answer> saveAnswers(String part) {

            return null;
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
