package org.theShire.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.*;
import org.theShire.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    UserRepository userRepository;
    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        Set<String> knowledges1 = new HashSet<>();
        Set<String> knowledges2 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        knowledges2.add("Test");
        knowledges2.add("adult cardiothoracic anesthesiology");
        user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),  new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void findByName_ShouldReturnUser_WhenUserExists() {
        Set<User> foundUsers = userRepository.findByName(new Name("Bilbo"));
        assertEquals(1, foundUsers.size());
        assertTrue(foundUsers.contains(user1));
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {
        Optional<User> foundUser = userRepository.findByEmail(new Email("Bilbo@hobbit.orc"));
        assertTrue(foundUser.isPresent());
        assertEquals(user1, foundUser.get());
    }

    @Test
    void findByEmail_ShouldThrow_WhenUserDoesNotExist() {

        Optional<User> foundUser = userRepository.findByEmail(new Email("Test@mail.orci"));
        assertFalse(foundUser.isPresent());
    }
}
