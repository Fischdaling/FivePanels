package org.theShire.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.richType.*;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.*;

import org.theShire.foundation.DomainAssertion;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.*;
import static org.theShire.service.UniversalService.enterUUID;

public class UserService {
    public static final UserRepository userRepo = new UserRepository();
    public static UserRelationShip relations = new UserRelationShip(); // Important or Error
    public static User userLoggedIn = null;

    public static void deleteUserById () {
        UUID userId = enterUUID("Enter User Id");
        isInCollection(userId,userRepo.getEntryMap().keySet(),"User not found",exTypeUser);
        userRepo.deleteById(userId);

    }

        public static void findByName () {
        System.out.println("Enter name");
        String name = scanner.nextLine();
        Set<User> user = userRepo.findByName(new Name(name));

        System.out.println(isNotNull(user,"user",exTypeUser));
    }

        public static void relationCommands () {
        System.out.println("1. See Incoming");
        System.out.println("2. send request");
        System.out.println("3. accept request");
        int answer = scanner.nextInt();
        scanner.nextLine();
        switch (answer) {
            case 1:
                User user1 = userLoggedIn;
                isTrue(userRepo.getEntryMap().containsKey(user1.getEntityId()),()->"User not Found",exTypeUser);
                    Set<User> incomingRequests = UserRelationShip.getRequest(user1);
                    isTrue(incomingRequests.isEmpty(),()->"No Request",exTypeUser);
                        incomingRequests.forEach((sender) -> System.out.println("Request from: " + sender.getProfile().getFirstName()));
                break;

            case 2:

                User sender2 = userLoggedIn;
                UUID receiverUUID2 = enterUUID("Enter target's Id");
                User receiver2 = userRepo.findByID(receiverUUID2);
                isTrue(userRepo.getEntryMap().containsKey(receiverUUID2),()->"Receiver not found.",exTypeUser);
                    UserRelationShip.sendRequest(sender2, receiver2);
                    System.out.println("Request sent from " + sender2.getProfile().getFirstName() + " to " + receiver2.getProfile().getFirstName());

                break;

            case 3:
                User sender3 = userLoggedIn;
                isTrue(userRepo.getEntryMap().containsKey(sender3.getEntityId()),()->"Sender not found.",exTypeUser);

                    UUID receiverUUID3 = enterUUID("Enter target's id");
                    User receiver3 = userRepo.findByID(receiverUUID3);
                    isTrue(UserRelationShip.getRequest(sender3).contains(receiver3),()->"Receiver not found.", exTypeUser);
                        UserRelationShip.acceptRequest(sender3, receiver3);
                        System.out.println("Request from " + sender3.getProfile().getFirstName() + " " + sender3.getEntityId() + " to " + receiver3.getProfile().getFirstName() + " accepted.");
                break;

            default:
                System.out.println("Invalid option.");
                break;
        }
    }

        public static User addUser () {
        System.out.println("Enter Firstname");
        String firstname = scanner.nextLine();
        System.out.println("Enter Lastname");
        String lastname = scanner.nextLine();
        System.out.println("Enter Email");
        String email = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        System.out.println("Confirm Password");
        String confirmPassword = scanner.nextLine();
        isEqual(confirmPassword, password, "passwords", exTypeUser);
        System.out.println("Enter Language");
        String language = scanner.nextLine();
        System.out.println("Enter Location");
        String location = scanner.nextLine();
        System.out.println("Enter Picture path");
        String profilePic = scanner.nextLine();
        System.out.println("How many educational titles do you want to add?");
        int i = scanner.nextInt();
        scanner.nextLine();
        String[] title = new String[i];
        for (int j = 0; j < i; j++) {
            System.out.println("Enter Title");
            title[j] = scanner.nextLine();
        }
        System.out.println("How many specialties do you want to add?");
        i = scanner.nextInt();
        Knowledges.getLegalKnowledges().forEach(System.out::println);
        System.out.println();
        scanner.nextLine();
        Set<String> specialty = new HashSet<>();
        for (int j = 0; j < i; j++) {
            System.out.println("Enter Specialty:");
            String value = scanner.nextLine();
            specialty.add(value);
        }
        return createUser(null, firstname, lastname, email, password, language, location, profilePic, specialty, title);
    }


        public static User createUser (UUID uuid, String firstname, String lastname, String email, String
        password, String language, String location, String picture, Set < String > specialization, String...
        educationalTitle){
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        Name firstName = new Name(firstname);
        Name lastName = new Name(lastname);
        Email emayl = new Email(email);
        Password passwort = new Password(password);
        Language lang = new Language(language);
        Location loc = new Location(location);
        List<EducationalTitle> titles = Arrays.stream(educationalTitle).map(EducationalTitle::new).toList();
        Media media = new Media(500, 400, picture, "500x400");
        Set<Knowledges> knowledges = specialization.stream().map(Knowledges::new).collect(Collectors.toSet());
        UserProfile profile = new UserProfile(lang, loc, media, firstName, lastName, titles);
        User user = new User(uuid, passwort, emayl, profile, knowledges);
        userRepo.save(user);
        return user;
    }

        public static User init () {


        System.out.println("1. Login");
        System.out.println("2. Create new User");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                scanner.nextLine();
                return login();
            case 2:
                scanner.nextLine();
                return addUser();
            case 0:
                System.exit(0);
            default:
                System.out.println("Invalid option.");
        }
        throw new MedicalDoctorException("Unexpected Error");
    }

        public static User login () {
            System.out.println("Enter Email: ");
            String email = scanner.nextLine();
            System.out.println("Enter Password: ");
            String password = scanner.nextLine();
            Optional<User> userOpt = userRepo.findByEmail(new Email(email));

            isTrue(userOpt.isPresent(),()->"User not found.", exTypeUser);
                User user = userOpt.get();
                                                            //The Password entered //The Password from the User
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().value());
            isTrue(result.verified,()->"Invalid password.",exTypeUser);
            return user;

    }
}
