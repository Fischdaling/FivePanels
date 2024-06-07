package org.theShire.presentation;

import org.theShire.domain.media.Content;
import org.theShire.domain.media.ContentText;
import org.theShire.domain.media.Media;
import org.theShire.domain.medicalCase.Answer;
import org.theShire.domain.medicalCase.Case;
import org.theShire.domain.medicalCase.CaseVote;
import org.theShire.domain.medicalDoctor.User;
import org.theShire.domain.medicalDoctor.UserRelationShip;
import org.theShire.domain.messenger.Chat;
import org.theShire.domain.messenger.Message;
import org.theShire.domain.richType.*;
import org.theShire.service.CaseService;
import org.theShire.service.UniversalService;
import org.theShire.service.UserService;

import java.util.*;

import static org.theShire.domain.medicalDoctor.UserRelationShip.createMapKey;
import static org.theShire.domain.medicalDoctor.UserRelationShip.relationShip;
import static org.theShire.service.CaseService.caseRepo;
import static org.theShire.service.UniversalService.*;
import static org.theShire.service.UserService.userLoggedIn;
import static org.theShire.service.UserService.userRepo;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Press Enter to start...");
        String[] arguments = scanner.nextLine().split(" ");

        try {
            initData();
            initUser();
        }catch (Throwable e) {
            System.err.println(e.getMessage());
            main(arguments);
        }

        // THE SHOW MUST GO ON
        while (true) {
            try {
                inputHandler();
            } catch (Throwable e) {
                System.err.println(e.getMessage());//swallow catch them all
                main(arguments);
            }
        }
    }

    private static void inputHandler() {
            System.out.println("Commands");
            System.out.println("1. add Doctor");
            System.out.println("2. add Case");
            System.out.println("3. Open Chat by id");
            System.out.println("4. View entities");
            System.out.println("5. Find Doctor by name");
            System.out.println("6. Find Case by id");
            System.out.println("7. Delete Doctor by id");
            System.out.println("8. Delete Case by id");
            System.out.println("9. manage Relations");
            System.out.println("10. Vote for Case Answer");
            System.out.println("11. Leave a like for a Case");
            System.out.println("12. Save Data");
            System.out.println("13. Change Profile");
            System.out.println("14. Set correct Answer");
            System.out.println("15. remove member from Case");
            System.out.println("16. add member to Case");
            System.out.println("0. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    UserPresentation.addUser();
                    break;
                case 2:
                    CasePresentation.addCase();
                    break;
                case 3:
                    ChatPresentation.openChat();
                    break;
                case 4:
                    UniversalPresentation.findAll();
                    break;
                case 5:
                    UserPresentation.findByName();
                    break;
                case 6:
                    CasePresentation.findCaseById();
                    break;
                case 7:
                    UserPresentation.deleteUserById();
                    break;
                case 8:
                    CasePresentation.deleteCaseById();
                    break;
                case 9:
                    UserPresentation.relationCommands();
                    break;
                case 10:
                    CasePresentation.vote();
                    break;
                case 11:
                    CasePresentation.likeCase();
                    break;
                case 12:
                    UniversalService.saveEntry();
                    break;
                case 13:
                    UserPresentation.changeProfile();
                    break;
                case 14:
                    CasePresentation.correctAnswer();
                    break;
                case 15:
                    CasePresentation.removeMember();
                    break;
                case 16:
                    CasePresentation.addMember();
                    break;
                case 0:
                    System.out.println("Goodbye");
                    userLoggedIn = UserPresentation.init();
                    break;
                default:
                    System.out.println("Invalid choice");

        }
    }
}
