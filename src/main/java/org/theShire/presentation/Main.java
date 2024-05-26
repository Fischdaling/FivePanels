package org.theShire.presentation;

import org.theShire.domain.exception.MediaException;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final UserRepository userRepo = new UserRepository();
    public static final CaseRepository caseRepo = new CaseRepository();
    public static final MessangerRepository messangerRepo = new MessangerRepository();



    public static User createUser(String firstname, String lastname, String email, String password,String language,String location,String picture,String...educationalTitle){
        Name firstName = new Name(firstname);
        Name lastName = new Name(lastname);
        Email emayl = new Email(email);
        Password passwort = new Password(password);
        Language lang = new Language(language);
        Location loc = new Location(location);
        List<EducationalTitle> titles = Arrays.stream(educationalTitle).map(EducationalTitle::new).toList();
        Media media = new Media(500,400,picture,"500x400");

        UserProfile profile = new UserProfile(lang,loc,media,firstName,lastName,titles);
        User user = new User(passwort,emayl,profile);
        userRepo.save(user);
        return user;
    }

    public static Case createCase(User owner,String title, List<Content> content, User... members){
           Case medCase = new Case(owner, title, content, members);
           owner.addOwnedCase(medCase);
        Arrays.stream(members).forEach(user -> user.addMemberOfCase(medCase));
           caseRepo.save(medCase);
           return medCase;
    }

    public static Chat createChat(User...chatters){
         Chat chat = new Chat(chatters);
         messangerRepo.save(chat);
         return chat;
    }

    public static void createPendingRelation(User user1, User user2){
       Relation rel1 = new Relation(user1,user2, Relation.RelationType.OUTGOING);
       Relation rel2 = new Relation(user2,user1, Relation.RelationType.INCOMING);
    }
    public static void createEstablishedRelation(User user1, User user2){
       Relation rel1 = new Relation(user1,user2, Relation.RelationType.ESTABLISHED);
       Relation rel2 = new Relation(user2,user1, Relation.RelationType.ESTABLISHED);
    }

    public static void main(String[] args) {

//        Media media = new Media("/Bilbo_Profile.png");
//        BufferedImage img = media.getImage();
        //CREATE USER1 -----------------------------------------------------------------------
        User user1 = createUser("Bilbo","Beutlin","Bilbo@hobbit.com","VerySafe123","Hobbitisch","Auenland","Bilbo Profile","Fassreiter","Meister Dieb");
        //CREATE USER2-----------------------------------------------------------------
        User user2 = createUser("Aragorn","Arathorn","Aragorn@gondor.at","EvenSaver1234","Gondorisch","Gondor","Aragorn Profile","Arathorns Sohn","König von Gondor");
        //CREATE USER3-----------------------------------------------------------------
        User user3 = createUser("Gandalf","Wizardo","Gandalf@Wizardo.out","ICastFireBall!","all","world", "Gandalf Profile","The Gray","The White","Ainur");
        //init Content
        List<Content> contents = new ArrayList<>();
        //add texts
        contents.add(new Content(new ContentText("My First Text")));
        contents.add(new Content(new ContentText("My Second Text")));
        //add Media
        contents.add(new Content(new Media(200,100,"My First Media", "200x100")));

        //Create a Case with user2&user3 as member and user1 as owner
        Case case1 = createCase(user1,"my First Case", contents,user2,user3);

        UserRelationShip relationShip = new UserRelationShip();
        relationShip.addRequest(user1,user2, Relation.RelationType.OUTGOING);
        relationShip.addRequest(user1,user2, Relation.RelationType.INCOMING);
        
        relationShip.addRequest(user2,user3, Relation.RelationType.OUTGOING);
        relationShip.addRequest(user3,user2, Relation.RelationType.INCOMING);


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
                    Optional<User> user = userRepo.findByName(new Name(name));
                    if(user.isPresent()){
                        System.out.println(user);
                    }
                    break;
                case 6:
                    System.out.println("Enter Id");
                    String caseId = scanner.nextLine();
                    caseRepo.findByID(UUID.fromString(caseId));
                    break;
                case 7:
                    System.out.println("Enter Id");
                    String userId = scanner.nextLine();
                    userRepo.deleteById(UUID.fromString(userId));
                    break;
                case 8:
                    System.out.println("Enter Id");
                    String delCaseId = scanner.nextLine();
                    caseRepo.deleteById(UUID.fromString(delCaseId));
                    break;
//                case 9:
//                    relationCommands();
//                    break;
                case 10:
                    vote();
                    break;
//                case 11:
//                    System.out.println("Goodbye");
//                    System.exit(0);
//                    break;
                case 12:
                    saveEntry();
                    break;
                case 0:
                    System.out.println("Goodbye");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    private static void saveEntry() {
        caseRepo.saveEntryMap("src/main/java/org/theShire/repository/caseRepoCSV.csv");
        messangerRepo.saveEntryMap("src/main/java/org/theShire/repository/chatRepoCSV.csv");
        userRepo.saveEntryMap("src/main/java/org/theShire/repository/userRepoCSV.csv");
    }

    private static void vote() {
        System.out.println("Enter your User ID");
        UUID userId = UUID.fromString(scanner.nextLine());
        System.out.println("Enter Case ID to Vote for");
        UUID caseId = UUID.fromString(scanner.nextLine());
        Case medCase = caseRepo.findByID(caseId);
        System.out.println("Enter Answer ID to Vote for");
        UUID voteId = UUID.fromString(scanner.nextLine());
        Answer answer = medCase.getCaseVote().getAnswers().stream().filter(answer1 -> answer1.equals(voteId)).findFirst().orElse(null);
        System.out.println("Enter the percent you want to vote this answer with");
        double percentage = scanner.nextDouble();
        System.out.println(medCase);
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
                    caseRepo.findAll().forEach(System.out::println);
                    break;
                case 3:
                    messangerRepo.findAll().forEach(System.out::println);
                    break;
            }

    }

    private static void openChat() {
        boolean exit = false;
        System.out.println("enter chat uuid");
        UUID uuid = UUID.fromString(scanner.nextLine());
        Chat chat = messangerRepo.findByID(uuid);
        System.out.println("chat with "+ chat.getPeople() +" opened");
        System.out.println(chat.getChatHistory());

        while(!exit){
            System.out.println("Enter your UUID");
            String user = scanner.nextLine();
            System.out.println("Send a message? true/false");
            exit = scanner.nextBoolean();
            System.out.println("What message?");
            String message = scanner.nextLine();
            chat.sendMessage(new Message(UUID.fromString(user),new Content(new ContentText(message))));
        }
    }

    private static void addCase() {
        System.out.println("How many possible Answers does the Case have");
        int ansCount = scanner.nextInt();
        LinkedHashSet<Answer> answer = new LinkedHashSet();
        for (int i = 0; i < ansCount; i++) {
            System.out.println("Enter Answer to the Case");
             answer.add(new Answer(scanner.nextLine()));
        }
        CaseVote caseVote = new CaseVote(answer);

        System.out.println("Enter Case Title");
        String title = scanner.nextLine();
        System.out.println("Enter Owner ID");
        UUID ownerId = UUID.fromString(scanner.nextLine());
        List<Content> caseContents = new ArrayList<>();

        contentUtil(caseContents);

        System.out.println("How many Doctors do you want to add?");
        int doctors = scanner.nextInt();
        String[] memberId = new String[doctors];
        for(int i = 0; i < doctors; i++){
            System.out.println("Enter Doctor ID");
            memberId[i] = scanner.nextLine();
        }
        User[] members = new User[memberId.length];
        for(int i = 0; i < memberId.length; i++){
        members[i] = userRepo.findByID(UUID.fromString(memberId[i]));
        }
        Case medCase = new Case(userRepo.findByID(ownerId),title,caseContents, members);

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
                    content.add(new Content(new Media(scanner.nextLine())));
                    break;
                case 2:
                    System.out.println("Enter Text");
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
        Knowledges[] specialty = new Knowledges[i];
        for (int j = 0; j < i; j++) {
                System.out.println("Enter Specialty");
                String value = scanner.nextLine();
                specialty[j] = new Knowledges(value);
        }
        User user = createUser(firstname,lastname,email,password,language,location,profilePic ,title);
        user.setSpecialization(specialty);


    }

}
