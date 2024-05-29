package org.theShire.presentation;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.Relation;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserProfile;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.CaseRepository;
import org.theShire.repository.MessangerRepository;
import org.theShire.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.domain.medicalDoctor.UserRelationShip.*;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final UserRepository userRepo = new UserRepository();
    public static final CaseRepository caseRepo = new CaseRepository();
    public static final MessangerRepository messangerRepo = new MessangerRepository();
    public static final UserRelationShip relationship = new UserRelationShip();



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

    //TODO

    public static Case createCase(User owner,String title,Set<String> knowledges, List<Content> content,CaseVote caseVote, User... members){
        Set<Knowledges> knowledgesSet  = knowledges.stream().map(Knowledges::new).collect(Collectors.toSet());
           Case medCase = new Case(owner, title, knowledgesSet, content, caseVote , members);
           owner.addOwnedCase(medCase);
        Arrays.stream(members).forEach(user -> user.addMemberOfCase(medCase));
           caseRepo.save(medCase);
           return medCase;
    }


    public static void main(String[] args) {

//        Media media = new Media("/Bilbo_Profile.png");
//        BufferedImage img = media.getImage();
        //CREATE USER1 -----------------------------------------------------------------------
        Set<String> knowledges1 = new HashSet<>();
        knowledges1.add("Test");
        knowledges1.add("adult cardiothoracic anesthesiology");
        User user1 = createUser(UUID.fromString("bf3f660c-0c7f-48f2-bd5d-553d6eff5a91"),"Bilbo","Beutlin","Bilbo@hobbit.com","VerySafe123","Hobbitisch","Auenland","Bilbo Profile",knowledges1,"Fassreiter","Meister Dieb");
        //CREATE USER2-----------------------------------------------------------------
        Set<String> knowledges2 = new HashSet<>();
        knowledges2.add("critical care or pain medicine");
        knowledges2.add("pediatric anesthesiology");
        User user2 = createUser(UUID.fromString("ba0a64e5-5fc9-4768-96d2-ad21df6e94c2"),"Aragorn","Arathorn","Aragorn@gondor.at","EvenSaver1234","Gondorisch","Gondor","Aragorn Profile",knowledges2,"Arathorns Sohn","König von Gondor");
        //CREATE USER3-----------------------------------------------------------------
        Set<String> knowledges3 = new HashSet<>();
        knowledges3.add("pediatric emergency medicine");
        knowledges3.add("hand surgery");
        User user3 = createUser(UUID.fromString("c3fc1109-be28-4bdc-8ca0-841e1fa4aee2"),"Gandalf","Wizardo","Gandalf@Wizardo.out","ICastFireBall!","all","world", "Gandalf Profile",knowledges3,"The Gray","The White","Ainur");

        sendRequest(user1,user2);
        acceptRequest(user1,user2);
        sendRequest(user3,user1);

        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200,100,"My First Media", "200x100")));

        //Create a Case with user2&user3 as member and user1 as owner
        Set<String> knowledges4 = new HashSet<>();
        knowledges4.add("pediatric emergency medicine");
        knowledges4.add("critical care or pain medicine");
        LinkedHashSet<Answer> answers = new LinkedHashSet<>();
        answers.add(new Answer("Answer 1"));
        answers.add(new Answer("Answer 2"));
        Case case1 = createCase(user1,"my First Case",knowledges4, contents,new CaseVote(answers),user2,user3);


        while(true){
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
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    addUser();
                    break;
                case 2:
                    addCase();
                    break;
                case 3:
                    openChat();
                    break;
                case 4:
                    findAll();
                    break;
                case 5:
                    System.out.println("Enter name");
                    String name = scanner.nextLine();
                    Set<User> user = userRepo.findByName(new Name(name));
                    if(user != null){
                        System.out.println(user);
                    }else{
                        System.out.println("User not found");
                    }
                    break;
                case 6:
                    Case medicCase =  caseRepo.findByID(enterUUID("Enter Case Id"));
                    if (caseRepo.getEntryMap().containsValue(medicCase)) {
                        medicCase.setViewcount(medicCase.getViewcount() + 1);
                        System.out.println(medicCase);
                    }else {
                        System.out.println("Case not found");
                    }
                    break;
                case 7:
                    UUID userId = enterUUID("Enter User Id");
                    if(userRepo.getEntryMap().containsKey(userId)){
                        userRepo.deleteById(userId);
                    }else {
                        System.out.println("User not found");
                    }
                    break;
                case 8:
                    Case tmpCase = caseRepo.findByID(enterUUID("Enter Case Id"));
                    if (caseRepo.getEntryMap().containsValue(tmpCase)) {
                        tmpCase.getOwner().removeCase(tmpCase);
                        tmpCase.getMembers().forEach(aUser -> aUser.removeCase(tmpCase));
                        caseRepo.deleteById(tmpCase.getEntityId());
                    }else {
                        System.out.println("Case not found");
                    }
                    break;
                case 9:
                    relationCommands();
                    break;
                case 10:
                    vote();
                    break;
                case 11:
                    Case medCase = caseRepo.findByID(enterUUID("Enter Case Id"));
                    if (caseRepo.getEntryMap().containsValue(medCase)) {
                        medCase.setViewcount(medCase.getViewcount()+1);
                        medCase.like(enterUUID("Enter Your Id"));
                    }else {
                        System.out.println("Case not found");
                    }
                    break;
                case 12:
                    saveEntry();
                    break;
                case 13:
                    loadEntry();
                    break;
                case 0:
                    System.out.println("Goodbye");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    private static void loadEntry() {
//        messangerRepo.loadEntryMap("src/main/java/org/theShire/persistence/chatRepoCSV.csv");
        userRepo.loadEntryMap("src/main/java/org/theShire/persistence/userLoadTest.csv");
        caseRepo.loadEntryMap("src/main/java/org/theShire/persistence/caseLoadTest.csv");
    }


    private static void relationCommands() {
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
                        incomingRequests.forEach((sender) -> {
                            System.out.println("Request from: " + sender.getProfile().getFirstName());
                        });
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

    private static void saveEntry() {
        caseRepo.saveEntryMap("src/main/java/org/theShire/persistence/caseRepoCSV.csv");
        messangerRepo.saveEntryMap("src/main/java/org/theShire/persistence/chatRepoCSV.csv");
        userRepo.saveEntryMap("src/main/java/org/theShire/persistence/userRepoCSV.csv");
    }

    private static void vote() {
        UUID userId = enterUUID("Enter your User ID");
        while (!caseRepo.getEntryMap().containsKey(userId)) {
            System.out.println("User not found");
            userId = enterUUID("Enter your User ID");
        }
        UUID caseId = enterUUID("Enter Case ID to Vote for");
        while (!caseRepo.getEntryMap().containsKey(caseId)) {
            System.out.println("Case not found");
            caseId = enterUUID("Enter Case ID");
        }
        Case medCase = caseRepo.findByID(caseId);
        for (Answer answer : medCase.getCaseVote().getAnswers()) {
            System.out.println(answer.getName() + System.lineSeparator());
            System.out.println(answer.getEntityId()+ System.lineSeparator());
        }
        UUID voteId = enterUUID("Enter Answer ID to Vote for");
        Answer answer = medCase.getCaseVote().getAnswers().stream().filter(answer1 -> answer1.equals(voteId)).findFirst().orElse(null);
        System.out.println("Enter the percent you want to vote this answer with");
        double percentage = scanner.nextDouble();
        medCase.getCaseVote().voting(userId, answer, percentage);
    }

    private static void findAll() {
        System.out.println("Enter Entity");
        System.out.println("1. Doctor");
        System.out.println("2. Case");
        System.out.println("3. Chat");
        int entityId = scanner.nextInt();
            switch (entityId){
                case 1:
                    userRepo.findAll().forEach(System.out::println);
                    break;
                case 2:
                    caseRepo.findAll().forEach(aCase -> aCase.setViewcount(aCase.getViewcount()+1));
                    caseRepo.findAll().forEach(System.out::println);
                    break;
                case 3:
                    messangerRepo.findAll().forEach(System.out::println);
                    break;
                default:
                    System.out.println("invalid command");
            }

    }

    private static void openChat() {
        boolean exit = false;
        UUID uuid = enterUUID("Enter chat uuid");
        Chat chat = messangerRepo.findByID(uuid);
        if (messangerRepo.getEntryMap().containsKey(uuid)) {
            System.out.println("chat with " + chat.getPeople() + " opened");
            System.out.println(chat.getChatHistory());

            while (!exit) {
                System.out.println("Enter your UUID");
                String user = scanner.nextLine();
                System.out.println("Send a message? true/false");
                exit = scanner.nextBoolean();
                System.out.println("Enter your Message?");
                String message = scanner.nextLine();
                chat.sendMessage(new Message(UUID.fromString(user), new Content(new ContentText(message))));
            }
        }else {
            System.out.println("Chat not found");
        }
    }

    private static void addCase() {
        System.out.println("How many possible Answers does the Case have");
        int ansCount = scanner.nextInt();
        String[] answers = new String[ansCount];
        LinkedHashSet<Answer> answer = new LinkedHashSet();
        scanner.nextLine();
        for (int i = 0; i < ansCount; i++) {
            System.out.println("Enter Answer to the Case");

            answers[i] = scanner.nextLine();
        }
        CaseVote caseVote = new CaseVote(answer);

        System.out.println("Enter Case Title");
        String title = scanner.nextLine();
        UUID ownerId = enterUUID("Enter Owner ID");
        List<Content> caseContents = new ArrayList<>();

        contentUtil(caseContents);

        System.out.println("How many Doctors do you want to add?");
        int doctors = scanner.nextInt();
        User[] members = new User[doctors];
        scanner.nextLine();
        for (int i = 0; i < doctors; i++) {
            members[i] = userRepo.findByID(enterUUID("Enter Member id"));
        }

        for (int i = 0; i < ansCount; i++) {
            answer.add(new Answer(answers[i]));
        }

        System.out.println("How many Knowledges do you want to add?");
        int knowledges = scanner.nextInt();
        Set<String> knowledgesSet= new HashSet<>();
        scanner.nextLine();
        for(int i = 0; i < knowledges; i++){
            System.out.println("Enter Knowledge");
            knowledgesSet.add(scanner.nextLine());
        }
        createCase(userRepo.findByID(ownerId),title,knowledgesSet,caseContents,caseVote,members);


    }

    private static List<Content> contentUtil(List<Content> content) {
        while(true) {
            System.out.println("Do you want to add Content true/false");
            boolean addContent = scanner.nextBoolean();
            if (!addContent) {
                break;
            }
            System.out.println("1. Media Content");
            System.out.println("2. Text Content");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Filepath");
                    scanner.nextLine();
                    content.add(new Content(new Media(scanner.nextLine())));
                    break;
                case 2:
                    System.out.println("Enter Text");
                    scanner.nextLine();
                    content.add(new Content(new ContentText(scanner.nextLine())));
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }
        return content;
    }

    private static void addUser() {
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

    private static UUID enterUUID(String enterMessage){
        StringBuilder str = new StringBuilder();
        for (User user : userRepo.findAll()) {
            str.append(user.getProfile().getFirstName()).append(" ").append(user.getProfile().getLastName()).append(System.lineSeparator());
            str.append(user.getEntityId()).append(System.lineSeparator());
        }
        for (Case medCase : caseRepo.findAll()) {
            str.append(medCase.getTitle()).append(System.lineSeparator());
            str.append(medCase.getEntityId()).append(System.lineSeparator());
        }

        for (Chat chat : messangerRepo.findAll()) {
            str.append(chat.getPeople().stream().map(aChat -> aChat.getProfile().getFirstName()).collect(Collectors.toSet()));
            str.append(chat.getEntityId()).append(System.lineSeparator());
        }
        System.out.println(str);

        System.out.println(enterMessage);
        return UUID.fromString(scanner.nextLine());

    }

}
