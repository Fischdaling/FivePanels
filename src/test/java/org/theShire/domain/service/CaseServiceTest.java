package org.theShire.domain.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalCase.Vote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.*;
import org.theShire.service.CaseService;
import org.theShire.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.CaseService.removeMember;
import static org.theShire.service.UserService.userLoggedIn;
import static org.theShire.service.UserService.userRepo;

public class CaseServiceTest {
    Case medCase;
    User user1;
    User user2;
    Answer a1;
    Answer a2;

    @BeforeEach
    public void setUp(){
        Set<String> knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        //CREATE USER2-----------------------------------------------------------------
        Set<String> knowledges2 = new HashSet<>();
        knowledges2.add("critical care or pain medicine");
        knowledges2.add("pediatric anesthesiology");
        user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),  new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");
        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200, 100, "My First Media", "200x100")));

        //Create a Case with user2&user3 as member and user1 as owner
        Set<String> knowledges4 = new HashSet<>();
        knowledges4.add("pediatric emergency medicine");
        knowledges4.add("critical care or pain medicine");
        LinkedHashSet<Answer> answers = new LinkedHashSet<>();
        a1 = new Answer("Answer 1");
        answers.add(a1);
        a2 = new Answer("Answer 2");
        answers.add(a2);
        medCase = CaseService.createCase(user1, "my First Case", knowledges4, contents, new CaseVote(answers), user2);

        userLoggedIn = user1;


    }


    @Test
    public void testCreateCase_ShouldAddUserToRepo_WhenCreated(){

        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("Test")));
        contents.add(new Content(new ContentText("TEst2")));
        //add Media
        contents.add(new Content(new Media(200, 100, "My First Media", "200x100")));

        Set<String> knowledges4 = new HashSet<>();
        knowledges4.add("pediatric emergency medicine");
        knowledges4.add("critical care or pain medicine");
        LinkedHashSet<Answer> answers = new LinkedHashSet<>();
        answers.add(new Answer("Answer 1"));
        answers.add(new Answer("Answer 2"));
        Case testCase = CaseService.createCase(user1, "my First Case", knowledges4, contents, new CaseVote(answers), user2);

        assertEquals(caseRepo.findByID(testCase.getEntityId()),testCase);
    }

    @Test
    public void testDeleteCaseById_ShouldRemoveUserFromCaseRepo_WhenCreated(){
        CaseService.deleteCaseById(medCase.getEntityId());
        assertNotEquals(caseRepo.findByID(medCase.getEntityId()),medCase);
    }

    @Test
    public void like_ShouldIncreaseLikeCounterByOne_WhenLiked(){
        userLoggedIn = user2;
        int oldLike = medCase.getLikeCount();
        CaseService.likeCase(medCase.getEntityId());

        assertEquals(oldLike+1,medCase.getLikeCount());
    }

    @Test
    public void like_ShouldAddUserToUserLiked_WhenLiked() {
        userLoggedIn = user2;
        CaseService.likeCase(medCase.getEntityId());

        assertTrue(medCase.getUserLiked().contains(UserService.userLoggedIn.getEntityId()));
    }

    @Test
    public void findCaseById_ShouldIncreaseViewCountByOne_WhenFound(){
        int oldView = medCase.getViewcount();
        CaseService.findCaseById(medCase.getEntityId());

        assertEquals(oldView+1,medCase.getViewcount());
    }

    @Test
    public void findCaseById_ShouldThrow_WhenNotFound(){
        int oldView = medCase.getViewcount();


        assertThrows(exTypeCase,()-> CaseService.findCaseById(UUID.randomUUID()));
        assertEquals(oldView,medCase.getViewcount());
    }

    @Test
    public void correctAnswer_ShouldIncreaseScoreOfUser_WhenUserVotedAnswer(){
        int oldScore = user1.getScore();
        System.out.println(medCase.getCaseVote().getAnswers());

        medCase.getCaseVote().voting(user1.getEntityId(),a1,25);
        medCase.getCaseVote().voting(user1.getEntityId(),a1,25);
        medCase.getCaseVote().voting(user1.getEntityId(),a2,50);
        medCase.declareCorrectAnswer(a1);
        assertEquals(oldScore +(2*50/100+1),user1.getScore());
    }

    @Test
    public void correctAnswer_ShouldThrow_WhenUserVotedMoreThan100percent(){
        System.out.println(medCase.getCaseVote().getAnswers());

        assertThrows(exTypeCase,()->medCase.getCaseVote().voting(user1.getEntityId(),a1,105));
    }

    @Test
    public void removeMember_ShouldRemoveMemberFromCase_WhenOwner(){
        CaseService.removeMember(medCase.getEntityId(), user2);

        assertFalse(medCase.getUserLiked().contains(user2.getEntityId()));
        assertFalse(user2.isMemberOfCases().contains(medCase));
    }

    @Test
    public void removeMember_ShouldThrowException_WhenNotOwner(){
        userLoggedIn = user2;
        assertThrows(exTypeCase, () -> {
            CaseService.removeMember(medCase.getEntityId(), user2);
        });
    }

    @Test
    public void addMember_ShouldAddMemberToCase_WhenOwner(){
        CaseService.removeMember(medCase.getEntityId(), user2);
        CaseService.addMember(medCase.getEntityId(), user2);

        assertTrue(user2.isMemberOfCases().contains(medCase));
        assertTrue(medCase.getMembers().contains(user2));
        assertTrue(medCase.getGroupchat().getPeople().contains(user2));
    }

    @Test
    public void addMember_ShouldThrowException_WhenNotOwner(){
        CaseService.removeMember(medCase.getEntityId(), user2);
        userLoggedIn = user2;
        assertThrows(RuntimeException.class, () -> {
            CaseService.addMember(medCase.getEntityId(), user2);
        });
    }

    @Test
    public void vote_ShouldVoteForAnswers_WhenValid(){
        userLoggedIn = user2;

        List<Answer> answers = Arrays.asList(a1, a2);
        List<Double> percentages = Arrays.asList(50.0, 50.0);
        CaseService.vote(medCase.getEntityId(), answers, percentages);

        Map<UUID, Set<Vote>> votes = medCase.getCaseVote().getVotes();
        assertTrue(votes.containsKey(userLoggedIn.getEntityId()));
        assertEquals(2, votes.get(userLoggedIn.getEntityId()).size());
        assertTrue(votes.get(userLoggedIn.getEntityId()).stream().allMatch(vote ->
                (vote.getAnswer().equals(a1) && vote.getPercent() == 50.0) ||
                        (vote.getAnswer().equals(a2) && vote.getPercent() == 50.0)
        ));
    }

    @Test
    public void vote_ShouldThrowException_WhenCaseNotFound(){
        userLoggedIn = user2;
        UUID uuid = UUID.randomUUID();
        List<Answer> answers = Arrays.asList(a1, a2);
        List<Double> percentages = Arrays.asList(50.0, 50.0);

        assertThrows(exTypeCase, () -> {
            CaseService.vote(uuid, answers, percentages);
        });
    }

    @Test
    public void vote_ShouldThrowException_WhenUserNotMember(){
        userLoggedIn = user2;
        if (userRepo.existsById(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"))){
            userRepo.deleteById(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"));
        }
        Set<String> knowledges3 = new HashSet<>();
        knowledges3.add("pediatric emergency medicine");
        knowledges3.add("hand surgery");
        User user3 = UserService.createUser(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"), new Name("Gandalf"), new Name("Wizardo"), new Email("Gandalf@Wizardo.beard"), new Password("ICastFireBall!"), new Language("all"), new Location("world"), "Gandalf Profile", knowledges3, "The Gray", "The White", "Ainur");

        List<Answer> answers = Arrays.asList(a1, a2);
        List<Double> percentages = Arrays.asList(50.0, 50.0);

        assertThrows(exTypeCase, () -> {
            CaseService.vote(user3.getEntityId(), answers, percentages);
        });
    }

    @Test
    public void vote_ShouldThrowException_WhenPercentagesNot100(){
        List<Answer> answers = Arrays.asList(a1,a2);
        List<Double> percentages = Arrays.asList(50.0, 40.0);

        assertThrows(exTypeCase, () -> {
            CaseService.vote(medCase.getEntityId(), answers, percentages);
        });
    }

}
