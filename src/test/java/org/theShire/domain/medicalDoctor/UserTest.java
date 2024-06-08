package org.theShire.domain.medicalDoctor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.richType.*;
import org.theShire.service.CaseService;
import org.theShire.service.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.domain.medicalDoctor.UserRelationShip.relationShip;

public class UserTest {
    User user1;
    User user2;
    Case case1;
    Set<Case> set;

    @BeforeEach
    void setUp(){
        relationShip = new HashMap<>();
        Set<String> knowledges1 = new HashSet<>();
        Set<String> knowledges2 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        knowledges2.add("Test");
        knowledges2.add("adult cardiothoracic anesthesiology");
        user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),  new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");

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
        answers.add(new Answer("Answer 1"));
        answers.add(new Answer("Answer 2"));
        case1 = CaseService.createCase(UUID.fromString("5a563273-bed3-4e8c-9c68-6a0229c11ce7"),user1, "my First Case", knowledges4, contents, new CaseVote(answers), user2);

         set = new HashSet<>();
    }

    @Test
    public void removeCase_ShouldDeleteRefrences_WhenCaseDeleted() {
        user1.removeCase(case1);
        user2.removeCase(case1);

        assertEquals(set, user1.getOwnedCases());
        assertEquals(set, user2.isMemberOfCases());
    }

    @Test
    public void addOwnedCase_ShouldAddCase_WhenCalled() {
        user1.addOwnedCase(case1);

        assertTrue(user1.getOwnedCases().contains(case1));
    }

    @Test
    public void removeOwnedCase_ShouldRemoveCase_WhenCalled() {
        user1.addOwnedCase(case1);

        user1.removeCase(case1);

        assertFalse(user1.getOwnedCases().contains(case1));
    }

    @Test
    public void addMemberOfCase_ShouldAddCase_WhenCalled() {
        user1.addMemberOfCase(case1);

        assertTrue(user1.isMemberOfCases().contains(case1));
    }

    @Test
    public void removeMemberOfCase_ShouldRemoveCase_WhenCalled() {
        user1.addMemberOfCase(case1);

        user1.removeCase(case1);

        assertFalse(user1.isMemberOfCases().contains(case1));
    }

}
