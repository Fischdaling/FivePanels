package org.theShire.service;

import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.*;

public class UserService {
    public static final UserRepository userRepo = new UserRepository();

    public static void deleteUserById() {
        UUID userId = enterUUID("Enter User Id");
        if(userRepo.getEntryMap().containsKey(userId)){
            userRepo.deleteById(userId);
        }else {
            System.out.println("User not found");
        }
    }

    public static void findByName() {
        System.out.println("Enter name");
        String name = scanner.nextLine();
        Set<User> user = userRepo.findByName(new Name(name));
        if(user != null){
            System.out.println(user);
        }else{
            System.out.println("User not found");
        }
    }

    public static void relationCommands() {
        System.out.println("1. See Incoming");
        System.out.println("2. send request");
        System.out.println("3. accept request");
        int answer = scanner.nextInt();
        scanner.nextLine();
        switch (answer) {
            case 1:
                UUID userUUID1 = enterUUID("Enter your ID");
                User user1 = userRepo.findByID(userUUID1);
                if (userRepo.getEntryMap().containsKey(userUUID1)) {

                    Set<User> incomingRequests = UserRelationShip.getRequest(user1);
                    if (incomingRequests.isEmpty()) {
                        System.out.println("No Requests");
                    } else {
                        incomingRequests.forEach((sender) -> System.out.println("Request from: " + sender.getProfile().getFirstName()));
                    }
                } else {
                    System.out.println("User not found.");
                }
                break;

            case 2:
                UUID senderUUID2 = enterUUID("Enter your Id");
                User sender2 = userRepo.findByID(senderUUID2);
                if (userRepo.getEntryMap().containsKey(senderUUID2)) {
                    UUID receiverUUID2 = enterUUID("Enter target's Id");
                    User receiver2 = userRepo.findByID(receiverUUID2);
                    if (userRepo.getEntryMap().containsKey(receiverUUID2)) {
                        UserRelationShip.sendRequest(sender2, receiver2);
                        System.out.println("Request sent from " + sender2.getProfile().getFirstName() + " to " + receiver2.getProfile().getFirstName());

                    } else {
                        System.out.println("Receiver not found.");
                    }
                } else {
                    System.out.println("Sender not found.");
                }
                break;

            case 3:
                UUID senderUUID3 = enterUUID("Enter your Id");
                User sender3 = userRepo.findByID(senderUUID3);
                if (userRepo.getEntryMap().containsKey(senderUUID3)) {

                    UUID receiverUUID3 = enterUUID("Enter target's id");
                    User receiver3 = userRepo.findByID(receiverUUID3);
                    if (userRepo.getEntryMap().containsKey(receiverUUID3)) {
                        UserRelationShip.acceptRequest(sender3, receiver3);
                        System.out.println("Request from " + sender3.getProfile().getFirstName()+" "+ senderUUID3 + " to " + receiver3.getProfile().getFirstName() + " accepted.");
                    } else {
                        System.out.println("Receiver not found.");
                    }
                } else {
                    System.out.println("Sender not found.");
                }
                break;

            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    public static void addUser() {
        System.out.println("Enter Firstname");
        String firstname =scanner.nextLine();
        System.out.println("Enter Lastname");
        String lastname = scanner.nextLine();
        System.out.println("Enter Email");
        String email = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        System.out.println("Enter Language");
        String language = scanner.nextLine();
        System.out.println("Enter Location");
        String location = scanner.nextLine();
        System.out.println("Enter Picture path");
        String profilePic = scanner.nextLine();
        System.out.println("How many educational titels do you want to add?");
        int i = scanner.nextInt();
        scanner.nextLine();
        String[] title = new String[i];
        for (int j = 0; j < i; j++) {
            System.out.println("Enter Title");
            title[j] = scanner.nextLine();
        }
        System.out.println("How many specialties do you want to add?");
        i = scanner.nextInt();
        scanner.nextLine();
        Set<String> specialty= new HashSet<>();
        for (int j = 0; j < i; j++) {
            System.out.println("Enter Specialty");
            String value = scanner.nextLine();
            specialty.add(value);
        }
        User user = createUser(null,firstname,lastname,email,password,language,location,profilePic,specialty ,title);

    }



    public static User createUser(UUID uuid, String firstname, String lastname, String email, String password,String language,String location,String picture,Set<String> specialization,String...educationalTitle){
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
        Media media = new Media(500,400,picture,"500x400");
        Set<Knowledges> knowledges  = specialization.stream().map(Knowledges::new).collect(Collectors.toSet());
        UserProfile profile = new UserProfile(lang,loc,media,firstName,lastName,titles);
        User user = new User(uuid,passwort,emayl,profile,knowledges);
        userRepo.save(user);
        return user;
    }
}
