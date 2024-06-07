package org.theShire.service;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;
import org.theShire.presentation.UserPresentation;

import java.util.*;

import static org.theShire.domain.medicalDoctor.UserRelationShip.createMapKey;
import static org.theShire.domain.medicalDoctor.UserRelationShip.relationShip;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userLoggedIn;
import static org.theShire.service.UserService.userRepo;

public class UniversalService {

    public static void initData() {

         User user1;
         User user2;
         User user3;
         Case case1 = null;

        //CREATE USER1 -----------------------------------------------------------------------
        if (userRepo.existsById(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"))){
            userRepo.deleteById(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"));
        }
        Set<String> knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"),
                new Name("Bilbo"),
                new Name("Beutlin"),
                new Email("Bilbo@hobbit.orc"),
                new Password("VerySafe123"),
                new Language("Hobbitisch"),
                new Location("Auenland"),
                "Bilbo Profile",
                knowledges1,
                "Fassreiter",
                "Meister Dieb");

        //CREATE USER2-----------------------------------------------------------------
        if (userRepo.existsById(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"))){
            userRepo.deleteById(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"));
        }
        Set<String> knowledges2 = new HashSet<>();
        knowledges2.add("critical care or pain medicine");
        knowledges2.add("pediatric anesthesiology");
        user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),
                new Name("Aragorn"),
                new Name("Arathorn"),
                new Email("Aragorn@gondor.orc"),
                new Password("EvenSaver1234"),
                new Language("Gondorisch"),
                new Location("Gondor"),
                "Aragorn Profile",
                knowledges2,
                "Arathorns Sohn",
                "König von Gondor");

        //CREATE USER3-----------------------------------------------------------------
        if (userRepo.existsById(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"))){
            userRepo.deleteById(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"));
        }
        Set<String> knowledges3 = new HashSet<>();
        knowledges3.add("pediatric emergency medicine");
        knowledges3.add("hand surgery");
        user3 = UserService.createUser(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"),
                new Name("Gandalf"),
                new Name("Wizardo"),
                new Email("Gandalf@Wizardo.beard"),
                new Password("ICastFireBall!"),
                new Language("all"),
                new Location("world"),
                "Gandalf Profile",
                knowledges3,
                "The Gray",
                "The White", "Ainur");

        if (!relationShip.containsKey(createMapKey(user1,user2))){
            // Send a friend request
            ChatService.sendRequest(user1, user2);
            Chat chat = ChatService.acceptRequest(user1, user2);
            chat.sendMessage(new Message(user1.getEntityId(),new Content(new ContentText("When can we eat something?"))));
            chat.sendMessage(new Message(user2.getEntityId(),new Content(new ContentText("We already had breakfast"))));
            chat.sendMessage(new Message(user1.getEntityId(),new Content(new ContentText("But whats with the second breakfast? :("))));
        }


        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200, 100, "My First Media", "200x100")));
        if (case1 != null) {
            if (caseRepo.existsById(case1.getEntityId())) {
                caseRepo.deleteById(case1.getEntityId());
            }
        }
        //Create a Case with user2&user3 as member and user1 as owner
        Set<String> knowledges4 = new HashSet<>();
        knowledges4.add("pediatric emergency medicine");
        knowledges4.add("critical care or pain medicine");
        LinkedHashSet<Answer> answers = new LinkedHashSet<>();
        Answer a1 = new Answer("Cancer");
        Answer a2 = new Answer("Ebola");
        answers.add(a1);
        answers.add(a2);
        case1 = CaseService.createCase(user1,
                "my First Case",
                knowledges4,
                contents,
                new CaseVote(answers),
                user2,
                user3);

        case1.like(user2.getEntityId());
        case1.getCaseVote().voting(user2.getEntityId(),a1,70);
        case1.getCaseVote().voting(user2.getEntityId(),a2,30);
        case1.getCaseVote().voting(user3.getEntityId(),a1,20);
        case1.getCaseVote().voting(user3.getEntityId(),a2,80);


    }
    public static void initUser(){
        userLoggedIn = UserPresentation.init();
    }

}
