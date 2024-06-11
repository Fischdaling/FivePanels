package org.theShire.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.exception.MessengerException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;
import org.theShire.service.ChatService;
import org.theShire.service.UserService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.theShire.domain.medicalDoctor.Relation.RelationType.*;
import static org.theShire.domain.medicalDoctor.UserRelationShip.relationShip;
import static org.theShire.service.ChatService.messengerRepo;

class ChatServiceTest {

    Chat testChat;
    User testUser;
    User testUser2;
    Set<String> knowledges1;
    Set<String> knowledges2;

    @BeforeEach
    public void init() {
        relationShip = new HashMap<>();
        testChat = new Chat();

        knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");

        knowledges2 = new HashSet<>();
        knowledges2.add("critical care or pain medicine");
        knowledges2.add("pediatric anesthesiology");

        testUser = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        testUser2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"), new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");

    }


    @Test
    void testCreateChat_ShouldNotBeNull_WhenNewChatWasCreated() {
        ChatService.createChat(testUser, testUser2);

        assertNotEquals(messengerRepo.findAll(), null);
    }

    @Test
    void testCreateChat_ShouldBeTrue_WhenNewChatWasCreated() {
        ChatService.createChat(testUser, testUser2);
        Set<User> testSet = new HashSet<>();
        testSet.add(testUser);
        testSet.add(testUser2);

        assertTrue(messengerRepo.existsById(messengerRepo.findByMembers(testSet).getEntityId()));
    }

    @Test
    void testCreateChat_ShouldThrow_WhenOneUserIsNotAssigned() {
        assertThrows(MessengerException.class, () -> {
            ChatService.createChat(testUser, null);
        });
    }

    @Test
    public void testSendMessage_ShouldSendMessage_WhenCalled() {
        Message message = new Message(UUID.randomUUID(), new Content(new ContentText("Hello, world!")));
        testChat.sendMessage(message);
        assertTrue(testChat.getChatHistory().contains(message));
    }

    @Test
    void testSendRequest_ShouldCreateRelationShip_WhenRequestSent() {
        ChatService.sendRequest(testUser, testUser2);

        String keyOutgoing = UserRelationShip.createMapKey(testUser, testUser2);
        String keyIncoming = UserRelationShip.createMapKey(testUser2, testUser);

        assertTrue(relationShip.containsKey(keyOutgoing));
        assertTrue(relationShip.containsKey(keyIncoming));
        assertEquals(OUTGOING, relationShip.get(keyOutgoing).getType());
        assertEquals(INCOMING, relationShip.get(keyIncoming).getType());
    }

    @Test
    void testSendRequest_ShouldThrowException_WhenSenderOrReceiverIsNull() {
        assertThrows(MedicalDoctorException.class, () -> {
            ChatService.sendRequest(null, testUser2);
        });
        assertThrows(MedicalDoctorException.class, () -> {
            ChatService.sendRequest(testUser, null);
        });
    }

    @Test
    void testAcceptRequest_ShouldCreateChat_WhenRequestAccepted() {
        ChatService.sendRequest(testUser, testUser2);
        Chat chat = ChatService.acceptRequest(testUser, testUser2);
        assertNotNull(chat);
        assertTrue(chat.getPeople().contains(testUser));
        assertTrue(chat.getPeople().contains(testUser2));
    }

    @Test
    void testAcceptRequest_ShouldEstablishRelation_WhenRequestAccepted() {
        ChatService.sendRequest(testUser, testUser2);
        ChatService.acceptRequest(testUser2, testUser);

        String keyOutgoing = UserRelationShip.createMapKey(testUser, testUser2);
        String keyIncoming = UserRelationShip.createMapKey(testUser2, testUser);

        assertEquals(ESTABLISHED, relationShip.get(keyOutgoing).getType());
        assertEquals(ESTABLISHED, relationShip.get(keyIncoming).getType());
    }


    @Test
    void testDeclineRequest_ShouldRemoveRelation_WhenRequestDeclined() {
        ChatService.sendRequest(testUser, testUser2);
        ChatService.declineRequest(testUser, testUser2);

        String keyOutgoing = UserRelationShip.createMapKey(testUser, testUser2);
        String keyIncoming = UserRelationShip.createMapKey(testUser2, testUser);

        assertFalse(UserRelationShip.relationShip.containsKey(keyOutgoing));
        assertFalse(UserRelationShip.relationShip.containsKey(keyIncoming));
    }


}