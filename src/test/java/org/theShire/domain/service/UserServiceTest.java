    package org.theShire.domain.service;

    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.theShire.domain.exception.MedicalDoctorException;
    import org.theShire.domain.medicalDoctor.User;
    import org.theShire.domain.richType.*;
    import org.theShire.service.UserService;

    import java.util.HashSet;
    import java.util.Set;
    import java.util.UUID;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
    import static org.theShire.domain.medicalDoctor.UserRelationShip.relationShip;
    import static org.theShire.service.CaseService.caseRepo;
    import static org.theShire.service.UserService.*;

    public class UserServiceTest {
        User user1;
        User user2;

        @BeforeEach
        public void setUp(){
            Set<String> knowledges1 = new HashSet<>();
            knowledges1.add("Test");
            knowledges1.add("adult cardiothoracic anesthesiology");
            user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), new Name("Bilbo"), new Name("Beutlin"), new Email("Bilbo@hobbit.orc"), new Password("VerySafe123"), new Language("Hobbitish"), new Location("Auenland"), "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
            Set<String> knowledges2 = new HashSet<>();
            knowledges2.add("critical care or pain medicine");
            knowledges2.add("pediatric anesthesiology");
            user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),  new Name("Aragorn"), new Name("Arathorn"), new Email("Aragorn@gondor.orc"), new Password("EvenSaver1234"), new Language("Gondorisch"), new Location("Gondor"), "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");
            userLoggedIn = user1;
        }

        @AfterEach
        public void tearDown() {
            relationShip.clear();
            user1 = null;
            user2 = null;
            caseRepo.deleteAll();
            userRepo.deleteAll();
        }

        @Test
        public void testCreateUser_ShouldAddUserToRepo_WhenCorrectlyFilled(){
            UUID uuid = UUID.randomUUID();
            Set<String> knowledges3 = new HashSet<>();
            knowledges3.add("pediatric emergency medicine");
            knowledges3.add("hand surgery");
            User user3 = UserService.createUser(uuid, new Name("Gandalf"), new Name("Wizardo"), new Email("Gandalf@Wizardo.beard"), new Password("ICastFireBall!"), new Language("all"), new Location("world"), "Gandalf Profile", knowledges3, "The Gray", "The White", "Ainur");

            assertEquals(userRepo.findByID(uuid),user3);
        }
        @Test
        public void testCreateUser_ShouldThrowMedicalDoctorException_WhenWrongParamter(){
            UUID uuid = UUID.randomUUID();
            Set<String> knowledges3 = new HashSet<>();
            knowledges3.add("pediatric emergency medicine");
            knowledges3.add("hand surgery");

            assertThrows(MedicalDoctorException.class,()->{
                UserService.createUser(uuid, new Name(null), new Name("Wizardo"), new Email("Gandalf@Wizardo.beard"), new Password("ICastFireBall!"), new Language("all"), new Location("world"), "Gandalf Profile", knowledges3, "The Gray", "The White", "Ainur");
            });
        }

        @Test
        public void testDeleteUser_ShouldRemoveUserFromRepo_WhenCalled(){
            UserService.deleteUserById(user1.getEntityId());

            assertFalse(userRepo.existsById(user1.getEntityId()));
        }

        @Test
        public void testDeleteUser_ShouldThrowMedicalDoctorException_WhenWrongParameter(){
            assertThrows(MedicalDoctorException.class, ()->UserService.deleteUserById(UUID.randomUUID()));
        }



        @Test
        public void login_ShouldReturnLoggedInUser_WhenUserExistsAndPasswordEquals(){
            User userReturned = login(user1.getEmail().value(),"VerySafe123");
            assertEquals(user1, userReturned);
        }

        @Test
        public void login_ShouldThrow_WhenUserExistsAndPasswordNotEquals(){
            assertThrows(exTypeUser,()->login(user1.getEmail().value(),"VerySafe"));
        }


        @Test
        public void findByName_ShouldReturnUsers_WhenUsersExist() {
            String name = "Bilbo";

            Set<User> result = UserService.findByName(name);
            assertTrue(result.contains(user1));
        }

        @Test
        public void findByName_ShouldThrow_WhenUsersDoesntExist() {
            String name = "Grimar";

            Set<User> result = UserService.findByName(name);
            assertFalse(result.contains(user1));
            assertTrue(result.isEmpty());
        }

        @Test
        public void cancelFriendship_ShouldCancelFriendship_WhenIncoming() {
            sendRequest(user1, user2);
            cancelFriendship(user1, user2);
            Set<User> incoming = seeIncoming(user2);

            assertNull(incoming);
        }

    }
