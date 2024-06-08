package org.theShire.presentation;

import org.theShire.domain.exception.MedicalCaseException;
import org.theShire.domain.media.Content;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.richType.Name;
import org.theShire.foundation.Knowledges;
import org.theShire.service.CaseService;

import java.util.*;

import static org.theShire.domain.exception.MedicalCaseException.exTypeCase;
import static org.theShire.foundation.DomainAssertion.*;
import static org.theShire.presentation.Main.scanner;
import static org.theShire.presentation.UniversalPresentation.enterUUID;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.UserService.userLoggedIn;
import static org.theShire.service.UserService.userRepo;

public class CasePresentation {

    public static void findAllCase() {
        CaseService.findAllCase();
        caseRepo.findAll().forEach(System.out::println);
    }

    public static void deleteCaseById() {
        UUID tmpCase = enterUUID("Enter Case Id", Case.class);
        CaseService.deleteCaseById(tmpCase);

    }

    public static void likeCase() {
        UUID medCase = enterUUID("Enter Case Id", Case.class);
        CaseService.likeCase(medCase);
    }

    public static void findCaseById() {
        UUID medicCase = enterUUID("Enter Case Id", Case.class);
        CaseService.findCaseById(medicCase);
    }

    public static void vote() {
        List<Answer> answers = new ArrayList<>();
        List<Double> percentages = new ArrayList<>();
        UUID caseId = enterUUID("Enter Case ID to Vote for", Case.class);
        isInCollection(caseId, caseRepo.getEntryMap().keySet(), () -> "Case not Found", exTypeCase);
        isInCollection(userLoggedIn, caseRepo.findByID(caseId).getMembers(), () -> "You are not able to vote", exTypeCase);
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
            Optional<Answer> answer = medCase.getCaseVote().getAnswers().stream().filter(answer1 -> answer1.getName().equals(userAnswer)).findFirst();
            isTrue(answer.isPresent(), () -> "Answer not found", exTypeCase);
            answers.add(answer.get());

            System.out.println("Enter the percent you want to vote this answer with");
            double percentage = scanner.nextDouble();
            lesserThan(percentage, 101.0, "percentage", exTypeCase);
            percentages.add(percentage);
            percentTrack += percentage;
            if (percentTrack > 100.0)
                throw new MedicalCaseException("percentage above 100");
        }
        CaseService.vote(caseId, answers, percentages);
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


        System.out.println("Enter Case Title");
        String title = scanner.nextLine();
        UUID ownerId = userLoggedIn.getEntityId();
        List<Content> caseContents = new ArrayList<>();

        UniversalPresentation.contentUtil(caseContents);

        System.out.println("How many Doctors do you want to add?");
        int doctors = scanner.nextInt();
        User[] members = new User[doctors];
        scanner.nextLine();
        for (int i = 0; i < doctors; i++) {
            UUID uuid = enterUUID("Enter Member id", User.class);
            isNotEqual(uuid, ownerId, "Id", exTypeCase);
            members[i] = userRepo.findByID(uuid);

        }

        for (int i = 0; i < ansCount; i++) {
            answer.add(new Answer(answers[i]));
        }
        CaseVote caseVote = new CaseVote(answer);
        System.out.println("How many Knowledges do you want to add?");
        int knowledges = scanner.nextInt();
        greaterEqualsZero(knowledges, () -> "Case must have Knowledges", exTypeCase);

        Set<String> knowledgesSet = new HashSet<>();
        Knowledges.getLegalKnowledges().forEach(System.out::println);
        System.out.println();
        scanner.nextLine();
        for (int i = 0; i < knowledges; i++) {
            System.out.println("Enter Knowledge:");
            knowledgesSet.add(scanner.nextLine());
        }
        CaseService.createCase(null,userRepo.findByID(ownerId), title, knowledgesSet, caseContents, caseVote, members);

    }

    public static void correctAnswer() {
        UUID caseId = enterUUID("Enter Case ID", Case.class);
        if (!caseRepo.findByID(caseId).isCaseDone()) {
            isTrue(caseRepo.findByID(caseId).getOwner().equals(userLoggedIn), () -> "You must be the owner of the case", exTypeCase);
            System.out.println(caseRepo.findByID(caseId).getCaseVote().getAnswers() + "\n");
            System.out.println("Enter Correct Answer");
            String answer = scanner.nextLine();
            CaseService.correctAnswer(caseId, answer);
            System.out.println(answer + " Was declared as the right Answer. Doctors that made this assumption will earn points.");
            System.out.println("Answer Voted the most was " + caseRepo.findByID(caseId).getCaseVote().getTop3Answer());
        }
    }

    public static User removeMember() {
        UUID medCaseId = enterUUID("Enter Case Id", Case.class);
        UUID memberId = enterUUID("Enter Member Id", User.class);
        User member = userRepo.findByID(memberId);
        CaseService.removeMember(medCaseId, member);
        System.out.println("Member was successfully removed");
        return member;
    }

    public static User addMember() {
        UUID medCaseId = enterUUID("Enter Case Id", Case.class);
        UUID memberId = enterUUID("Enter Member Id", User.class);
        User member = userRepo.findByID(memberId);
        CaseService.addMember(medCaseId, member);
        return member;
    }

}
