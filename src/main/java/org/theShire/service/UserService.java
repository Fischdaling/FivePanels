package org.theShire.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.*;
import static org.theShire.presentation.Main.scanner;
import static org.theShire.service.UniversalService.enterUUID;

public class UserService {
    public static final UserRepository userRepo = new UserRepository();
    public static UserRelationShip relations = new UserRelationShip(); // Important or Error
    public static User userLoggedIn = null;

    public static void deleteUserById () {
        UUID userId = enterUUID("Enter User Id");
        User user = userRepo.findByID(userId);
        isInCollection(userId,userRepo.getEntryMap().keySet(),"User not found",exTypeUser);
        userRepo.deleteById(userId);
        Set<Case> medCase = user.isMemberOfCases();
        medCase.forEach(mCase -> mCase.removeMember(user));
    }

    public static void findByName () {
        System.out.println("Enter name");
        String name = scanner.nextLine();
        Set<User> user = userRepo.findByName(new Name(name));

        System.out.println(isNotNull(user,"user",exTypeUser));
    }

    public static void relationCommands () {
        User sender = userLoggedIn;
        isTrue(userRepo.getEntryMap().containsKey(sender.getEntityId()),()->"Sender not found.",exTypeUser);

        System.out.println("1. See Incoming");
        System.out.println("2. send request");
        System.out.println("3. accept request");
        System.out.println("4. decline request or remove friend");
        int answer = scanner.nextInt();
        scanner.nextLine();
        switch (answer) {
            case 1:
                    Set<User> incomingRequests = UserRelationShip.getRequest(sender);
                    if (incomingRequests.isEmpty()){
                        System.out.println("No Request");
                    }else
                        incomingRequests.forEach((aSender) -> System.out.println("Request from: " + aSender.getProfile().getFirstName()));
                break;

            case 2:
                UUID receiverUUID2 = enterUUID("Enter target's Id");
                User receiver2 = userRepo.findByID(receiverUUID2);
                isTrue(userRepo.getEntryMap().containsKey(receiverUUID2),()->"Receiver not found.",exTypeUser);
                    UserRelationShip.sendRequest(sender, receiver2);
                    System.out.println("Request sent from " + sender.getProfile().getFirstName() + " to " + receiver2.getProfile().getFirstName());

                break;

            case 3:
                    UUID receiverUUID3 = enterUUID("Enter target's id");
                    User receiver3 = userRepo.findByID(receiverUUID3);
                    isTrue(UserRelationShip.getRequest(sender).contains(receiver3),()->"Receiver not found.", exTypeUser);
                        UserRelationShip.acceptRequest(sender, receiver3);
                        System.out.println("Request from " + sender.getProfile().getFirstName() + " " + sender.getEntityId() + " to " + receiver3.getProfile().getFirstName() + " accepted.");
                break;
            case 4:
                UUID receiverUUID4 = enterUUID("Enter target's id");
                User receiver4 = userRepo.findByID(receiverUUID4);
                    UserRelationShip.declineRequest(sender,receiver4);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public static User addUser () {

        System.out.println("Enter Email");
        String inEmail = scanner.nextLine();
        Email email = new Email(inEmail);
        System.out.println("Enter Password");
        String inPassword = scanner.nextLine();
        Password password = new Password(inPassword);
        System.out.println("Confirm Password");
        String inConfirmPassword = scanner.nextLine();
        isEqual(inConfirmPassword, inPassword, "passwords", exTypeUser);
        UserProfile profile = enterUserProfile();
        int i;
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
        String[] titles = profile.getEducationalTitles().stream().map(EducationalTitle::toString).toArray(String[]::new);
        return createUser(null, profile.getFirstName(), profile.getFirstName(), email, password, profile.getLanguage(), profile.getLocation(), profile.getProfilePicture().getAltText(), specialty,titles );
    }

    private static UserProfile enterUserProfile() {
        System.out.println("Enter Firstname");
        String inFirstName = scanner.nextLine();
        Name firstname = new Name(inFirstName);
        System.out.println("Enter Lastname");
        String inLastName = scanner.nextLine();
        Name lastname = new Name(inLastName);
        System.out.println("Enter Language");
        String inLanguage = scanner.nextLine();
        Language language = new Language(inLanguage);
        System.out.println("Enter Location");
        String inLocation = scanner.nextLine();
        Location location = new Location(inLocation);
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
        UserProfile profile = new UserProfile(language,location,new Media(profilePic),firstname,lastname,Arrays.stream(title).map(EducationalTitle::new).toList());
        return profile;
    }



    public static User createUser (UUID uuid, Name firstname, Name lastname, Email email, Password
        password, Language language, Location location, String picture, Set <String> specialization, String... educationalTitle){
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        Name firstName = firstname;
        Name lastName = lastname;
        Email emayl = email;
        Password passwort = password;
        Language lang = language;
        Location loc = location;
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
    
    public static void changeProfile () {
        User user = userLoggedIn;
        System.out.println(user.getProfile().toString());
        UserProfile profile = enterUserProfile();
        user.setProfile(profile);
    }
}
