package org.theShire.domain.medicalCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.*;
import org.theShire.service.CaseService;
import org.theShire.service.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.service.UserService.userLoggedIn;

public class CaseTest {
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

    }

    @Test
    public void testAddMember() {
        Set<String> knowledges = new HashSet<>();
        knowledges.add("critical care or pain medicine");
        knowledges.add("pediatric anesthesiology");
        User user3 = UserService.createUser(UUID.randomUUID(), new Name("Frodo"), new Name("Beutlin"), new Email("Frodo@hobbit.orc"), new Password("FrodoProved1234"), new Language("Hobbitish"), new Location("Auenland"), "Frodo Profile", knowledges, "Dr", "Hobbingen");
        medCase.addMember(user3);
        assertEquals(2, medCase.getMembers().size());
    }

    @Test
    public void testRemoveMember() {
        medCase.removeMember(user2);
        assertEquals(0, medCase.getMembers().size());
    }

    @Test
    public void testAddContent() {
        Content newContent = new Content(new ContentText("Another Text"));
        medCase.addContent(newContent);
        assertEquals(4, medCase.getContent().size());
    }


    @Test
    void declareCorrectAnswer_ValidAnswer_CorrectlyUpdatesUserScores() {
        User owner = medCase.getOwner();
        int initialOwnerScore = owner.getScore();
        int initialUser2Score = user2.getScore();
        medCase.getCaseVote().voting(user2.getEntityId(), a1, 50);
        medCase.declareCorrectAnswer(a1);

        assertEquals(initialOwnerScore + 5, owner.getScore());
        assertEquals(initialUser2Score + 2 * 50 / 100 + 1, user2.getScore());
    }

    @Test
    void declareCorrectAnswer_InvalidAnswer_ThrowsException() {
        Answer incorrectAnswer = new Answer("Wrong Answer");

        assertThrows(exTypeCase, () -> medCase.declareCorrectAnswer(incorrectAnswer));
    }

    @Test
    public void testAddNullMember() {
        assertThrows(exTypeCase, () -> medCase.addMember(null));
    }

    @Test
    public void testRemoveNonMember() {
        User nonMember = UserService.createUser(UUID.randomUUID(), new Name("Gollum"), new Name("Smeagol"), new Email("gollum@middleearth.orc"), new Password("MyPrecious123"), new Language("Gollumish"), new Location("Mount Doom"), "Gollum Profile", new HashSet<>(), "Stinker", "Twisted");
        assertThrows(exTypeCase, () -> medCase.removeMember(nonMember));
    }


}
