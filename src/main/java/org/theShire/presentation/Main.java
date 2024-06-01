package org.theShire.presentation;

import org.theShire.domain.exception.MedicalDoctorException;
import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.Relation;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.service.CaseService;
import org.theShire.service.ChatService;
import org.theShire.service.UniversalService;
import org.theShire.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.medicalDoctor.UserRelationShip.acceptRequest;
import static org.theShire.domain.medicalDoctor.UserRelationShip.sendRequest;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UniversalService.loadEntry;
import static org.theShire.service.UniversalService.saveEntry;
import static org.theShire.service.UserService.*;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Press Enter to start...");
        String[] arguments = scanner.nextLine().split(" ");
        initData();
        while (true) {
            try {
                inputHandler();
            } catch (Exception e) {
                System.err.println(e.getMessage());//swallow
                main(arguments);
            }
        }
    }

    public static void initData() {

//        Media media = new Media("/Bilbo_Profile.png");
//        BufferedImage img = media.getImage();
        //CREATE USER1 -----------------------------------------------------------------------
        Set<String> knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        User user1 = UserService.createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"), "Bilbo", "Beutlin", "Bilbo@hobbit.orc", "VerySafe123", "Hobbitisch", "Auenland", "Bilbo Profile", knowledges1, "Fassreiter", "Meister Dieb");
        //CREATE USER2-----------------------------------------------------------------
        Set<String> knowledges2 = new HashSet<>();
        knowledges2.add("critical care or pain medicine");
        knowledges2.add("pediatric anesthesiology");
        User user2 = UserService.createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"), "Aragorn", "Arathorn", "Aragorn@gondor.orc", "EvenSaver1234", "Gondorisch", "Gondor", "Aragorn Profile", knowledges2, "Arathorns Sohn", "König von Gondor");
        //CREATE USER3-----------------------------------------------------------------
        Set<String> knowledges3 = new HashSet<>();
        knowledges3.add("pediatric emergency medicine");
        knowledges3.add("hand surgery");
        User user3 = UserService.createUser(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"), "Gandalf", "Wizardo", "Gandalf@Wizardo.beard", "ICastFireBall!", "all", "world", "Gandalf Profile", knowledges3, "The Gray", "The White", "Ainur");
//TODO TESTI
        // Send a friend request
        UserRelationShip.sendRequest(user1, user2);
        UserRelationShip.acceptRequest(user1, user2);
        UserRelationShip.sendRequest(user2, user3);
        UserRelationShip.sendRequest(user3, user1);


        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200, 100, "My First Media", "200x100")));

        //Create a Case with user2&user3 as member and user1 as owner
        Set<String> knowledges4 = new HashSet<>();
        knowledges4.add("pediatric emergency medicine");
        knowledges4.add("critical care or pain medicine");
        LinkedHashSet<Answer> answers = new LinkedHashSet<>();
        answers.add(new Answer("Answer 1"));
        answers.add(new Answer("Answer 2"));
        Case case1 = CaseService.createCase(user1, "my First Case", knowledges4, contents, new CaseVote(answers), user2, user3);
    }



    private static void inputHandler() {
        userLoggedIn = init();
        //TODO Check Main and conditions... z.B. Owner kann kein member bei seinem case sein.
            System.out.println("Commands");
            System.out.println("1. add Doctor");
            System.out.println("2. add Case");
            System.out.println("3. Open Chat by id");
            System.out.println("4. View entities");
            System.out.println("5. Find Doctor by name");
            System.out.println("6. Find Case by id");
            System.out.println("7. Delete Doctor by id");
            System.out.println("8. Delete Case by id");
            System.out.println("9. manage Relations"); //TODO
            System.out.println("10. Vote for Case Answer");
            System.out.println("11. Leave a like for Case Answer");
            System.out.println("12. Save Data");
            System.out.println("13. Load Data");
            System.out.println("0. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    UserService.addUser();
                    break;
                case 2:
                    CaseService.addCase();
                    break;
                case 3:
                    ChatService.openChat();
                    break;
                case 4:
                    UniversalService.findAll();
                    break;
                case 5:
                    UserService.findByName();
                    break;
                case 6:
                    CaseService.findCaseById();
                    break;
                case 7:
                    UserService.deleteUserById();
                    break;
                case 8:
                    CaseService.deleteCaseById();
                    break;
                case 9:
                    UserService.relationCommands();
                    break;
                case 10:
                    CaseService.vote();
                    break;
                case 11:
                    CaseService.likeCase();
                    break;
                case 12:
                    saveEntry();
                    break;
                case 13:
                    loadEntry();
                    break;
                case 0:
                    System.out.println("Goodbye");
                    userLoggedIn = init();
                    break;
                default:
                    System.out.println("Invalid choice");

        }
    }
}
