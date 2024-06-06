package org.theShire.service;

import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.ChatService.messengerRepo;
import static org.theShire.service.UserService.userRepo;

public class UniversalService {

    public static void loadEntry() {
        messengerRepo.loadEntryMap("src/main/java/org/theShire/persistence/chatRepoCSV.csv");
        caseRepo.loadEntryMap("src/main/java/org/theShire/persistence/caseRepoCSV.csv");
        userRepo.loadEntryMap("src/main/java/org/theShire/persistence/userRepoCSV.csv");
//        System.out.println("Buy premium to unlock this feature");

    }

    public static void saveEntry() {
        caseRepo.saveEntryMap("src/main/java/org/theShire/persistence/caseRepoCSV.csv");
        messengerRepo.saveEntryMap("src/main/java/org/theShire/persistence/chatRepoCSV.csv");
        userRepo.saveEntryMap("src/main/java/org/theShire/persistence/userRepoCSV.csv");
    }

}
