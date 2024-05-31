package org.theShire.service;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.Name;
import org.theShire.foundation.Knowledges;
import org.theShire.repository.CaseRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.enterUUID;
import static org.theShire.presentation.Main.scanner;
import static org.theShire.service.UserService.userLoggedIn;
import static org.theShire.service.UserService.userRepo;


public class CaseService {
    public static final CaseRepository caseRepo = new CaseRepository();

    public static void deleteCaseById() {
        Case tmpCase = caseRepo.findByID(enterUUID("Enter Case Id"));
        if (caseRepo.getEntryMap().containsValue(tmpCase)) {
            tmpCase.getOwner().removeCase(tmpCase);
            tmpCase.getMembers().forEach(aUser -> aUser.removeCase(tmpCase));
            caseRepo.deleteById(tmpCase.getEntityId());
        }else {
            System.out.println("Case not found");
        }
    }

    public static void likeCase() {
        Case medCase = caseRepo.findByID(enterUUID("Enter Case Id"));
        if (caseRepo.getEntryMap().containsValue(medCase)) {
            medCase.setViewcount(medCase.getViewcount()+1);
            medCase.like(enterUUID("Enter Your Id"));
        }else {
            System.out.println("Case not found");
        }
    }

    public static void findCaseById() {
        Case medicCase =  caseRepo.findByID(enterUUID("Enter Case Id"));
        if (caseRepo.getEntryMap().containsValue(medicCase)) {
            medicCase.setViewcount(medicCase.getViewcount() + 1);
            System.out.println(medicCase);
        }else {
            System.out.println("Case not found");
        }
    }

    public static void vote() {
        UUID caseId = enterUUID("Enter Case ID to Vote for");
        while (!caseRepo.getEntryMap().containsKey(caseId)) {
            System.out.println("Case not found");
            caseId = enterUUID("Enter Case ID");
        }
        Case medCase = caseRepo.findByID(caseId);
        for (Answer answer : medCase.getCaseVote().getAnswers()) {
            System.out.println(answer.getName());
        }
        double percentTrack = 0.0;

        while (percentTrack < 100.0) {
            System.out.println("Enter Answer to vote for");
            if (percentTrack != 0.0)
                scanner.nextLine();
            String str = scanner.nextLine();
            Name userAnswer = new Name(str);
            Answer answer = medCase.getCaseVote().getAnswers().stream().filter(answer1 -> answer1.getName().equals(userAnswer)).findFirst().orElse(null);
            System.out.println("Enter the percent you want to vote this answer with");
            double percentage = scanner.nextDouble();
            percentTrack += percentage;
            if (percentTrack <= 100.0) {
                medCase.getCaseVote().voting(userLoggedIn.getEntityId(), answer, percentage);
            }else {
                medCase.getCaseVote().voting(userLoggedIn.getEntityId(), answer, percentTrack - percentage);
                percentTrack = 100.0;
            }
        }
    }

    public static void addCase() {
        System.out.println("How many possible Answers does the Case have");
        int ansCount = scanner.nextInt();
        String[] answers = new String[ansCount];
        LinkedHashSet<Answer> answer = new LinkedHashSet<>();
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
            UUID uuid = enterUUID("Enter Member id");
            if (uuid != ownerId) {
                members[i] = userRepo.findByID(uuid);
            }else{
                System.out.println("Member Cannot be owner");
            }
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

    public static Case createCase(User owner,String title,Set<String> knowledges, List<Content> content,CaseVote caseVote, User... members){
        Set<Knowledges> knowledgesSet  = knowledges.stream().map(Knowledges::new).collect(Collectors.toSet());
        Case medCase = new Case(owner, title, knowledgesSet, content, caseVote , members);
        owner.addOwnedCase(medCase);
        Arrays.stream(members).forEach(user -> user.addMemberOfCase(medCase));
        caseRepo.save(medCase);
        return medCase;
    }
}
