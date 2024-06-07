package org.theShire.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;
import org.theShire.service.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MessengerRepositoryTest {
    MessengerRepository messengerRepository;
    User user1;
    User user2;
    Chat chat;

    @BeforeEach
    void setUp() {
        Set<String> knowledges1 = new HashSet<>();
        Set<String> knowledges2 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        knowledges2.add("Test");
        knowledges2.add("adult cardiothoracic anesthesiology");
        user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),  new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");
        chat = new Chat(user1, user2);
        messengerRepository = new MessengerRepository();
        messengerRepository.save(chat);

    }

    @Test
    void findByMembers_ShouldReturnChat_WhenFound() {
        Set<User> members = new HashSet<>();
        members.add(user1);
        members.add(user2);

        Chat foundChat = messengerRepository.findByMembers(members);
        assertEquals(chat, foundChat);

        members = new HashSet<>();
        members.add(user2);
        members.add(user1);

        foundChat = messengerRepository.findByMembers(members);
        assertEquals(chat, foundChat);
    }

}
