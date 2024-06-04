package org.theShire.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;
import org.theShire.service.CaseService;
import org.theShire.service.ChatService;
import org.theShire.service.UniversalService;
import org.theShire.service.UserService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userRepo;

class UniversalServiceTest {
    User user1;
    User user2;
    Set<User> userSet;
    Case case1;
    Set<String> knowledges1;
    Answer a1;
    Answer a2;
    LinkedHashSet<Answer> answers;
    List<Content> contents;

    @BeforeEach
    public void init() {
        knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"), new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges1, "Arathorns Sohn", "König von Gondor");
        userSet = new HashSet<>();
        answers = new LinkedHashSet<>();
        a1 = new Answer("Answer 1");
        answers.add(a1);
        a2 = new Answer("Answer 2");
        answers.add(a2);
        contents = new ArrayList<>();
        case1 = CaseService.createCase(user1, "my First Case", knowledges1, contents, new CaseVote(answers), user1);
    }

//Just for fun lul
//    @Test
//    void testLoadEntry_ShouldTellUserToBuyPremium_WhenCalled() {
//        String test = System.console().readLine();
//        UniversalService.loadEntry();
//
//        assertEquals(test, "Buy premium to unlock this feature");
//    }

//    @Test //TODO find out how to read Console outputs called from another method... I suck
//    void testFindAll_ShouldDeliverAllUsers_WhenCalled() {
//        System.setIn(new ByteArrayInputStream("1".getBytes()));
//        UniversalService.findAll();
//        assertEquals(reader, );
//    }

//    @Test
//    void testFindAll_ShouldDeliverAllCases_WhenCalled() {
//        System.setIn(new ByteArrayInputStream("2".getBytes()));
//        UniversalService.findAll();
//        assertEquals(reader, );
//    }
//    @Test
//    void testFindAll_ShouldDeliverAllChats_WhenCalled() {
//        System.setIn(new ByteArrayInputStream("3".getBytes()));
//        UniversalService.findAll();
//        assertEquals(reader, );
//    }

    @Test
    void testEnterUUID_ShouldReturnRightUUID_WhenTryingToFindAUser() {
        System.setIn(new ByteArrayInputStream("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91".getBytes()));
        assertEquals(UniversalService.enterUUID("Please Enter an ID of a User"), userRepo.findByID(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91")).getEntityId());
    }

    @Test
    void testEnterUUID_ShouldReturnRightUUID_WhenTryingToFindACase() {
        UUID caseID = case1.getEntityId();
        System.setIn(new ByteArrayInputStream(caseID.toString().getBytes()));

        assertEquals(UniversalService.enterUUID("Please Enter an ID of a Case"), caseRepo.findByID(caseID).getEntityId());
    }

    @Test
    void testEnterUUID_ShouldReturnRightUUID_WhenTryingToFindAChat() {
        ChatService.createChat(user1, user2);
        userSet.add(user1);
        userSet.add(user2);

        UUID chatID = messengerRepo.findByMembers(userSet).getEntityId();
        System.setIn(new ByteArrayInputStream(chatID.toString().getBytes()));
        assertEquals(UniversalService.enterUUID("Please Enter an ID of a Chat"), messengerRepo.findByID(chatID).getEntityId());
    }
}