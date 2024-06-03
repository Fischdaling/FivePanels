package org.theShire.service;

import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.messenger.Chat;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.theShire.presentation.Main.scanner;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userRepo;

public class UniversalService {

    public static void loadEntry() {
//        messengerRepo.loadEntryMap("src/main/java/org/theShire/persistence/chatRepoCSV.csv");
//        caseRepo.loadEntryMap("src/main/java/org/theShire/persistence/caseRepoCSV.csv");
//        userRepo.loadEntryMap("src/main/java/org/theShire/persistence/userLoadTest.csv");
        System.out.println("Buy premium to unlock this feature");

    }

    public static void saveEntry() {
        caseRepo.saveEntryMap("src/main/java/org/theShire/persistence/caseRepoCSV.csv");
        messengerRepo.saveEntryMap("src/main/java/org/theShire/persistence/chatRepoCSV.csv");
        userRepo.saveEntryMap("src/main/java/org/theShire/persistence/userRepoCSV.csv");
    }

    public static void findAll() {
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
                messengerRepo.findAll().forEach(System.out::println);
                break;
            default:
                System.out.println("invalid command");
        }

    }


    public static UUID enterUUID(String enterMessage){
        StringBuilder str = new StringBuilder();

        str.append("User").repeat('-',20).append(System.lineSeparator());
        for (User user : userRepo.findAll()) {
            str.append(user.getProfile().getFirstName()).append(" ").append(user.getProfile().getLastName()).append(System.lineSeparator());
            str.append(user.getEntityId()).append(System.lineSeparator());
        }

        str.append("Cases").repeat('-',20).append(System.lineSeparator());
        for (Case medCase : caseRepo.findAll()) {
            str.append(medCase.getTitle()).append(System.lineSeparator());
            str.append(medCase.getEntityId()).append(System.lineSeparator());
        }

        str.append("Chat").repeat('-',20).append(System.lineSeparator());
        for (Chat chat : messengerRepo.findAll()) {
            str.append(chat.getPeople().stream().map(aChat -> aChat.getProfile().getFirstName()).collect(Collectors.toSet()));
            str.append(chat.getEntityId()).append(System.lineSeparator());
        }
        System.out.println(str);

        System.out.println(enterMessage);
        return UUID.fromString(scanner.nextLine());

    }
}
