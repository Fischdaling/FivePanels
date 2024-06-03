package org.theShire.domain.medicalDoctor;

import org.junit.jupiter.api.Test;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.service.CaseService;
import org.theShire.service.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    public void removeCase_ShouldDeleteRefrences_WhenCaseDeleted() {
        Set<String> knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        User user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), "Bilbo", "Beutlin", "Bilbo@hobbit.orc", "VerySafe123", "Hobbitisch", "Auenland", "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        User user2 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), "Bilbo", "Beutlin", "Bilbo@hobbit.orc", "VerySafe123", "Hobbitisch", "Auenland", "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");

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
        Case case1 = CaseService.createCase(user1, "my First Case", knowledges4, contents, new CaseVote(answers), user2);

        Set<Case> set = new HashSet<>();

        user1.removeCase(case1);
        user2.removeCase(case1);

        assertEquals(set, user1.getOwnedCases());
        assertEquals(set, user2.isMemberOfCases());
    }
}
