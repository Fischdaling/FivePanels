package org.theShire.presentation;

import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;
import org.theShire.service.UserService;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.theShire.domain.exception.MedicalDoctorException.exTypeUser;
import static org.theShire.foundation.DomainAssertion.isEqual;
import static org.theShire.foundation.DomainAssertion.isTrue;
import static org.theShire.presentation.Main.scanner;
import static org.theShire.presentation.UniversalPresentation.enterUUID;
import static org.theShire.service.UserService.userLoggedIn;
import static org.theShire.service.UserService.userRepo;

public class UserPresentation {
    public static void findAllUser() {
        UserService.findAllUser().forEach(System.out::println);
    }

    public static void deleteUserById() {
        UUID userId = enterUUID("Enter User Id", User.class);
        UserService.deleteUserById(userId);
    }

    public static void findByName() {
        System.out.println("Enter name");
        String name = scanner.nextLine();
        System.out.println(UserService.findByName(name));
    }

    public static void relationCommands() {
        User sender = userLoggedIn;
        User receiver;
        isTrue(userRepo.getEntryMap().containsKey(sender.getEntityId()), () -> "Sender not found.", exTypeUser);

        System.out.println("1. See Incoming");
        System.out.println("2. send request");
        System.out.println("3. accept request");
        System.out.println("4. decline request or remove friend");
        int answer = scanner.nextInt();
        scanner.nextLine();
        switch (answer) {
            case 1:
                UserService.seeIncoming(sender).forEach((aSender) -> System.out.println("Request from: " + aSender.getProfile().getFirstName()));
                break;

            case 2:
                receiver = UserService.findByID(enterUUID("Enter target's id", User.class));
                UserService.sendRequest(sender, receiver);
                System.out.println("Request sent from " + sender.getProfile().getFirstName() + " to " + receiver.getProfile().getFirstName());
                break;

            case 3:
                receiver = UserService.findByID(enterUUID("Enter target's id", User.class));
                UserService.acceptRequest(sender, receiver);
                System.out.println("Request from " + sender.getProfile().getFirstName() + " " + sender.getEntityId() + " to " + receiver.getProfile().getFirstName() + " accepted.");

                break;
            case 4:
                receiver = UserService.findByID(enterUUID("Enter target's id", User.class));
                UserService.cancelFriendship(sender, receiver);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }


    public static User addUser() {

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
        return UserService.createUser(null, profile.getFirstName(), profile.getFirstName(), email, password, profile.getLanguage(), profile.getLocation(), profile.getProfilePicture().getAltText(), specialty, titles);
    }

    public static void changeProfile() {
        User user = userLoggedIn;
        System.out.println(user.getProfile().toString());
        UserProfile profile = enterUserProfile();
        user.setProfile(profile);
        user.setUpdatedAt(Instant.now());
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
        UserProfile profile = UserService.updateProfile(language, location, new Media(profilePic), firstname, lastname, Arrays.stream(title).map(EducationalTitle::new).toList());
        return profile;
    }


    public static User init() {
        System.out.println("1. Login");
        System.out.println("2. Create new User");
        System.out.println("0. Exit");
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

    public static User login() {
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();
        return UserService.login(email, password);
    }

}
