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

        userLoggedIn = user2;


    }

    @AfterEach
    public void tearDown() {
        System.setIn(null);
        medCase = null;
        user1 = null;
        user2 = null;
        a1 = null;
        a2 = null;
        caseRepo.deleteAll();
        userRepo.deleteAll();

    }
    private void setInputStream(String input) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
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
    public void testDeleteCaseById_ShouldAddUserToCaseRepo_WhenCreated(){
        CaseService.deleteCaseById(medCase.getEntityId());
        assertNotEquals(caseRepo.findByID(medCase.getEntityId()),medCase);
    }

    @Test
    public void like_ShouldIncreaseLikeCounterByOne_WhenLiked(){
        int oldLike = medCase.getLikeCount();
        CaseService.likeCase(medCase.getEntityId() );

        assertEquals(oldLike+1,medCase.getLikeCount());
    }

    @Test
    public void like_ShouldAddUserToUserLiked_WhenLiked() {
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


        assertThrows(exTypeCase,()-> CaseService.findCaseById(medCase.getEntityId()));
        assertEquals(oldView,medCase.getViewcount());
    }

    @Test
    public void correctAnswer_ShouldIncreaseScoreOfUser_WhenUserVotedAnswer(){
        int oldScore = user1.getScore();
        System.out.println(medCase.getCaseVote().getAnswers());

        medCase.getCaseVote().voting(user1.getEntityId(),a1,25);
        medCase.getCaseVote().voting(user1.getEntityId(),a1,25);
        medCase.getCaseVote().voting(user1.getEntityId(),a2,50);
        medCase.setCorrectAnswer(a1);
        assertEquals(oldScore +(2*50/100+1),user1.getScore());
    }

    @Test
    public void correctAnswer_ShouldThrow_WhenUserVotedMoreThan100percent(){
        System.out.println(medCase.getCaseVote().getAnswers());

        assertThrows(exTypeCase,()->medCase.getCaseVote().voting(user1.getEntityId(),a1,105));
    }
}
